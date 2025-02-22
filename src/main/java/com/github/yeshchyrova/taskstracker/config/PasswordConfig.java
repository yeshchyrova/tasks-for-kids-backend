package com.github.yeshchyrova.taskstracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfig {

//  @Value("${DATABASE_URL:NOT_SET}")
//  private String databaseUrl;

  @Bean
  public PasswordEncoder passwordEncoder() {
//    System.out.println("databaseUrl: " + databaseUrl);
    return new BCryptPasswordEncoder();
  }
}