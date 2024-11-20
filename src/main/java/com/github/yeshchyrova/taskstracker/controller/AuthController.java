package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.config.UserAuthenticationProvider;
import com.github.yeshchyrova.taskstracker.dtos.CredentialsDto;
import com.github.yeshchyrova.taskstracker.dtos.SignUpDto;
import com.github.yeshchyrova.taskstracker.dtos.UserDto;
import com.github.yeshchyrova.taskstracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    return ResponseEntity.ok().body(userDto);
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
    UserDto createdUser = userService.register(user);
    createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
    return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
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















