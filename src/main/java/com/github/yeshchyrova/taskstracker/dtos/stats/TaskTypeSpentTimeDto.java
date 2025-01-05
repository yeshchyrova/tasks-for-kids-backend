package com.github.yeshchyrova.taskstracker.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTypeSpentTimeDto {
  private String taskType;
  private String spentTotal;
}
