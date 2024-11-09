package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.dtos.UserRegistrationRequest;
import com.github.yeshchyrova.taskstracker.service.ChildService;
import com.github.yeshchyrova.taskstracker.service.FamilyService;
import com.github.yeshchyrova.taskstracker.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
   private final FamilyService familyService;

   @PostMapping("/register")
   public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
      familyService.registerFamily(request);
      return ResponseEntity.ok("success");
   }
}
