package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.dtos.*;
import com.github.yeshchyrova.taskstracker.enums.Role;
import com.github.yeshchyrova.taskstracker.exceptions.AppException;
import com.github.yeshchyrova.taskstracker.helpers.email.EmailDetails;
import com.github.yeshchyrova.taskstracker.helpers.email.EmailService;
import com.github.yeshchyrova.taskstracker.entity.Family;
import com.github.yeshchyrova.taskstracker.entity.User;
import com.github.yeshchyrova.taskstracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final FamilyService familyService;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;

  private UserDto toUserDto(User user) {
    return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .login(user.getLogin())
            .role(user.getRole())
            .familyId(user.getFamilyId())
            .build();
  }

  public UserDto login(CredentialsDto credentialsDto) {
    User user = userRepository.findByLogin(credentialsDto.getLogin())
            .orElseThrow(() -> new AppException("Invalid login or password", HttpStatus.BAD_REQUEST));

    if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()),
                                user.getPassword())) {
      return toUserDto(user);
    }

    throw new AppException("Invalid login or password", HttpStatus.BAD_REQUEST);
  }

  @Transactional
  public UserDto register(SignUpDto usersObj) {
    Optional<User> optionalParent = userRepository.findByLogin(usersObj.getParentDto().getLogin());
    Optional<User> optionalChild = userRepository.findByLogin(usersObj.getChildDto().getLogin());

    if (optionalParent.isPresent()) {
      throw new AppException("Parent's login already exists", HttpStatus.BAD_REQUEST);
    }
    if (optionalChild.isPresent()) {
      throw new AppException("Child's login already exists", HttpStatus.BAD_REQUEST);
    }

    Family family = familyService.saveFamily();
    Long familyId = family.getId();

    User parent = new User();
    parent.setName(usersObj.getParentDto().getName());
    parent.setLogin(usersObj.getParentDto().getLogin());
    parent.setRole(Role.PARENT);
    parent.setPassword(
            passwordEncoder.encode(CharBuffer.wrap(usersObj.getParentDto().getPassword())));
    parent.setFamilyId(familyId);

    User savedParent = userRepository.save(parent);

    User child = new User();
    child.setName(usersObj.getChildDto().getName());
    child.setLogin(usersObj.getChildDto().getLogin());
    child.setRole(Role.CHILD);
    String childPassword = generateRandomPassword();
    child.setPassword(passwordEncoder.encode(CharBuffer.wrap(childPassword)));
    child.setFamilyId(familyId);
    userRepository.save(child);

    sendEmail(child.getLogin(), childPassword);

    return toUserDto(savedParent);
  }

  public UserDto findByLogin(String login) {
    User user = userRepository.findByLogin(login)
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    return toUserDto(user);
  }

  public String generateRandomPassword() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  public void sendEmail(String email, String password) {
    String msgBody = "Password: " + password;
    try {
      emailService.sendSimpleMail(new EmailDetails(email, msgBody, "Tasks Manager"));
    } catch (Exception e) {
      log.warn("Failed to send credentials email to {} via notification-service", email, e);
    }
  }

  public List<ChildByFamilyDto> findAllChildrenByFamilyId(Long familyId) {
    List<ChildByFamilyDto> children = userRepository.findAllByFamilyIdAndRole(familyId,
                                                                              Role.CHILD).stream()
            .map(user -> new ChildByFamilyDto(
                    user.getId(),
                    user.getName(),
                    user.getLogin(),
                    user.getFamilyId()))
            .toList();
    if (children.isEmpty()) throw new AppException("Children not found", HttpStatus.NOT_FOUND);
    return children;
  }


  public void addFamilyMember(NewMemberDto memberDto) {
    Optional<User> optional = userRepository.findByLogin(memberDto.getLogin());

    if (optional.isPresent()) {
      throw new AppException("This login already exists", HttpStatus.BAD_REQUEST);
    }

    User member = new User();
    member.setName(memberDto.getName());
    member.setLogin(memberDto.getLogin());
    member.setRole(memberDto.getRole());

    String memberPassword = generateRandomPassword();
    member.setPassword(passwordEncoder.encode(CharBuffer.wrap(memberPassword)));
    member.setFamilyId(memberDto.getFamilyId());
    userRepository.save(member);

    sendEmail(member.getLogin(), memberPassword);
  }
}