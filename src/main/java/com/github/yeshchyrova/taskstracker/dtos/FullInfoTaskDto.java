package com.github.yeshchyrova.taskstracker.dtos;

import com.github.yeshchyrova.taskstracker.entity.CompletedTask;
import com.github.yeshchyrova.taskstracker.enums.Mood;
import com.github.yeshchyrova.taskstracker.enums.Report;
import com.github.yeshchyrova.taskstracker.enums.Status;
import com.github.yeshchyrova.taskstracker.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullInfoTaskDto {
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
  private String photoReport = null;
  private String textReport = null;
  private LocalDateTime reportTime = null;
  private String spentTime = null;
  private Mood mood = null;

  public FullInfoTaskDto(TaskWithNamesDto task) {
    this.id = task.getId();
    this.title = task.getTitle();
    this.description = task.getDescription();
    this.deadline = task.getDeadline();
    this.childId = task.getChildId();
    this.parentId = task.getParentId();
    this.taskType = task.getTaskType();
    this.reportType = task.getReportType();
    this.status = task.getStatus();
    this.parentName = task.getParentName();
    this.childName = task.getChildName();
  }

  public void setCompletedTask(CompletedTask completedTask) {
    this.photoReport = completedTask.getPhotoReport();
    this.textReport = completedTask.getTextReport();
    this.reportTime = completedTask.getReportTime();
    this.spentTime = String.valueOf(completedTask.getSpentTime());
    this.mood = completedTask.getMood();
  }
}
