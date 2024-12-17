package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.dtos.ChildByFamilyDto;
import com.github.yeshchyrova.taskstracker.dtos.CredentialsDto;
import com.github.yeshchyrova.taskstracker.dtos.SignUpDto;
import com.github.yeshchyrova.taskstracker.dtos.UserDto;
import com.github.yeshchyrova.taskstracker.enums.Role;
import com.github.yeshchyrova.taskstracker.exceptions.AppException;
import com.github.yeshchyrova.taskstracker.helpers.email.EmailDetails;
import com.github.yeshchyrova.taskstracker.helpers.email.EmailServiceImpl;
import com.github.yeshchyrova.taskstracker.entity.Family;
import com.github.yeshchyrova.taskstracker.entity.User;
import com.github.yeshchyrova.taskstracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final FamilyService familyService;
  private final PasswordEncoder passwordEncoder;
  private final EmailServiceImpl emailService;


  public UserDto login(CredentialsDto credentialsDto) {
    User user = userRepository.findByLogin(credentialsDto.getLogin())

            //!!!!!!!  CHANGE MESSAGE TO "INVALID LOGIN OR PASSWORD"  !!!!!
            .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

    if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()),
                                user.getPassword())) {
      return new UserDto(user.getId(), user.getName(), user.getLogin(), user.getPassword(),
                         user.getRole(), user.getFamilyId());
    }

    //!!!!!!!  CHANGE MESSAGE TO "INVALID LOGIN OR PASSWORD"  !!!!!
    throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
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
    System.out.println("Random child password: " + childPassword);
    child.setPassword(passwordEncoder.encode(CharBuffer.wrap(childPassword)));
    child.setFamilyId(familyId);
    userRepository.save(child);


    sendEmail(child.getLogin(), childPassword);

    return new UserDto(savedParent.getId(), savedParent.getName(), savedParent.getLogin(),
                       savedParent.getPassword(),
                       savedParent.getRole(), savedParent.getFamilyId());
  }

  public UserDto findByLogin(String login) {
    System.out.println("login: " + login);
    User user = userRepository.findByLogin(login)
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    return new UserDto(user.getId(), user.getName(), user.getLogin(), user.getPassword(),
                       user.getRole(), user.getFamilyId());
  }

  public String generateRandomPassword() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  public void sendEmail(String email, String password) {
    String msgBody = "Password: " + password;
    emailService.sendSimpleMail(new EmailDetails(email, msgBody, "Tasks Manager"));
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
}