package com.github.yeshchyrova.taskstracker.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorDto {

  private String message;
}