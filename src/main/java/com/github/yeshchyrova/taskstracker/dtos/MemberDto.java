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
public class MemberDto {
  private String name;
  private String email;
  private Role role;
}
