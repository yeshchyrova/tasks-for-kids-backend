package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.config.UserAuthenticationProvider;
import com.github.yeshchyrova.taskstracker.dtos.CredentialsDto;
import com.github.yeshchyrova.taskstracker.dtos.SignUpDto;
import com.github.yeshchyrova.taskstracker.dtos.UserDto;
import com.github.yeshchyrova.taskstracker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

  private final UserService userService;
  private final UserAuthenticationProvider userAuthenticationProvider;

  @PostMapping("/login")
  public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
    UserDto userDto = userService.login(credentialsDto);
    userDto.setToken(userAuthenticationProvider.createToken(userDto));
    System.out.println(userDto);
    return ResponseEntity.ok().header("Authorization", userDto.getToken()).body(userDto);
  }

  @PostMapping("/api/logout")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    SecurityContextHolder.getContext().setAuthentication(null);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto usersObj) {
    UserDto createdParent = userService.register(usersObj);

    createdParent.setToken(userAuthenticationProvider.createToken(createdParent));
    return ResponseEntity.created(URI.create("/" + createdParent.getId()))
            .header("Authorization", createdParent.getToken())
            .body(createdParent);
  }

  @GetMapping("/users/current")
  public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String header) {
    if (header != null) {
      String[] authElements = header.split(" ");
      if (authElements.length == 2
              && "Bearer".equals(authElements[0])) {
        UserDto user = userAuthenticationProvider.getCurrentUser(authElements[1]);
        user.setToken(authElements[1]);
        return ResponseEntity.ok(user);
      }
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}














