package com.github.yeshchyrova.taskstracker.dtos;

import com.github.yeshchyrova.taskstracker.enums.Report;
import com.github.yeshchyrova.taskstracker.enums.Status;
import com.github.yeshchyrova.taskstracker.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskWithNamesDto {
  private Long id;
  private String title;
  private String description;
  private LocalDateTime deadline;
  private Long childId;
  private Long parentId;
  private Type taskType;
  private Report reportType;
  private Status status;
  private String parentName;
  private String childName;
}
