package com.github.yeshchyrova.taskstracker.dtos;

import com.github.yeshchyrova.taskstracker.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialsDto {

  private String email; // login
  private char[] password;
  private Role role;

}