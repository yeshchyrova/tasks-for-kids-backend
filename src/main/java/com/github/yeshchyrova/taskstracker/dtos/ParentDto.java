package com.github.yeshchyrova.taskstracker.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParentDto {
  String name;
  String login;
  String password;
  String role;
}
