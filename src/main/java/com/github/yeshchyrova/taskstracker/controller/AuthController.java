package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.config.UserAuthenticationProvider;
import com.github.yeshchyrova.taskstracker.dtos.CredentialsDto;
import com.github.yeshchyrova.taskstracker.dtos.ParentDto;
import com.github.yeshchyrova.taskstracker.dtos.SignUpDto;
import com.github.yeshchyrova.taskstracker.dtos.UserDto;
import com.github.yeshchyrova.taskstracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

//@RestController
//@RequiredArgsConstructor
//public class AuthController {
//  private final RegistrationService registrationService;
//  private final AuthService authService;
//
//  @PostMapping("/register")
//  public ResponseEntity<?> registerUser(@RequestBody SignUpDto request) {
////    System.out.println(request);
//    Map<String, Long> ids = registrationService.registerParentAndChild(request);
//    Long parentId = ids.get("parentId");
//    Long childId = ids.get("childId");
//// отправить на фронт урл и также объект родителя
//    return ResponseEntity.ok(Map.of("redirectUrl",
//                                    "/parent/" + parentId + "/" + childId));
//  }
//
//  @PostMapping("/login")
//  public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto request) {
//    return authService.authenticate(request.getEmail(), request.getPassword(), request.getRole());
//  }
//}















