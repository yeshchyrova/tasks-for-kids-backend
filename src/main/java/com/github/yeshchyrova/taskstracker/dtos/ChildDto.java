package com.github.yeshchyrova.taskstracker.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildDto {
  String name;
  String login;
  String role;
}
