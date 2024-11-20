package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.dtos.CredentialsDto;
import com.github.yeshchyrova.taskstracker.dtos.SignUpDto;
import com.github.yeshchyrova.taskstracker.dtos.UserDto;
import com.github.yeshchyrova.taskstracker.enums.Role;
import com.github.yeshchyrova.taskstracker.exceptions.AppException;
import com.github.yeshchyrova.taskstracker.model.User;
import com.github.yeshchyrova.taskstracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final Long familyId = 34L;

  public UserDto login(CredentialsDto credentialsDto) {
    User user = userRepository.findByLogin(credentialsDto.getLogin())
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

    if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()),
                                user.getPassword())) {
      return new UserDto(user.getId(), user.getLogin(), user.getPassword(), user.getRole(), familyId);
    }
    throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
  }

  public UserDto register(SignUpDto userDto) {
    Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

    if (optionalUser.isPresent()) {
      throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
    }

    User user = new User();
    user.setLogin(userDto.getLogin());
    if (Objects.equals(userDto.getRole(), "PARENT")) user.setRole(Role.PARENT);
    else if (Objects.equals(userDto.getRole(), "CHILD")) user.setRole(Role.CHILD); // PARENT
    else throw new AppException("Invalid role", HttpStatus.BAD_REQUEST);

    user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

    User savedUser = userRepository.save(user);

    return new UserDto(savedUser.getId(), savedUser.getLogin(), savedUser.getPassword(),
                       savedUser.getRole(), familyId);
  }

  public UserDto findByLogin(String login) {
    User user = userRepository.findByLogin(login)
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    return new UserDto(user.getId(), user.getLogin(), user.getPassword(), user.getRole(), familyId);
  }

}