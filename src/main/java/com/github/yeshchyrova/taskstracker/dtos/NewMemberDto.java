package com.github.yeshchyrova.taskstracker.dtos;

import com.github.yeshchyrova.taskstracker.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewMemberDto {
  String name;
  String login;
  Role role;
  Long familyId;
}
