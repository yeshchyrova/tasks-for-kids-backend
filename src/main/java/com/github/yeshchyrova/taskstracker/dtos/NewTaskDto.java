package com.github.yeshchyrova.taskstracker.dtos;

import com.github.yeshchyrova.taskstracker.enums.Report;
import com.github.yeshchyrova.taskstracker.enums.Status;
import com.github.yeshchyrova.taskstracker.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTaskDto {
  String title;
  String description = null;
  String deadline = null;
  Type taskType = Type.OTHER;
  Report reportType = null;
  Status status = Status.TODO;
  Long parentId;
  Long childId;
}
