package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.dtos.LoginRequestDto;
import com.github.yeshchyrova.taskstracker.dtos.RegistrationRequestDto;
import com.github.yeshchyrova.taskstracker.service.AuthService;
import com.github.yeshchyrova.taskstracker.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final RegistrationService registrationService;
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody RegistrationRequestDto request) {
    System.out.println(request);
    Map<String, Long> ids = registrationService.registerParentAndChild(request);
    Long parentId = ids.get("parentId");
    Long childId = ids.get("childId");
// отправить на фронт урл и также объект родителя
    return ResponseEntity.ok(Map.of("redirectUrl",
                                    "/parent/" + parentId + "/" + childId));
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto request) {
    return authService.authenticate(request.getEmail(), request.getPassword(), request.getRole());
  }
}















