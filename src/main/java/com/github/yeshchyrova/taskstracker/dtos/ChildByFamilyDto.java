package com.github.yeshchyrova.taskstracker.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildByFamilyDto {
  Long id;
  String name;
  String login;
  Long familyId;
}
