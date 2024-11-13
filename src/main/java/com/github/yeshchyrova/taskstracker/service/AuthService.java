package com.github.yeshchyrova.taskstracker.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;


  public ResponseEntity<?> authenticate(String email, String password, String role) {
    try {
      Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
      authentication = authenticationManager.authenticate(authentication);
      return ResponseEntity.ok("User authenticated");
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(401).body("Invalid email or password");
    } catch (AuthenticationException e) {
      return ResponseEntity.status(500).body("Authentication failed");
    }
  }
}
