package com.github.yeshchyrova.taskstracker.dtos;

import com.github.yeshchyrova.taskstracker.entity.CompletedTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullInfoTaskDto {
  TaskWithNamesDto task;
  CompletedTask completedTask;
}
