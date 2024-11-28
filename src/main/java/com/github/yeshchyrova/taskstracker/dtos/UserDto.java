package com.github.yeshchyrova.taskstracker.dtos;

import com.github.yeshchyrova.taskstracker.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
  private Long id;
  private String name;
  private String login;
  private String token;
  private Role role;
  private Long familyId;
}
