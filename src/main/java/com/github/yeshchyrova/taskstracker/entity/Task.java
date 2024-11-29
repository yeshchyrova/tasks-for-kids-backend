package com.github.yeshchyrova.taskstracker.entity;

import com.github.yeshchyrova.taskstracker.enums.Report;
import com.github.yeshchyrova.taskstracker.enums.Status;
import com.github.yeshchyrova.taskstracker.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//not completed task
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String title;

  @Size(max = 500)
  private String description;

//  @Column(name = "deadline_date")
//  private LocalDate deadlineDate;
//
//  @Column(name = "deadline_time")
//  private LocalTime deadlineTime;

  @Column(name = "deadline")
  @Future
  private LocalDateTime deadlineDateTime;

  @Column(name = "id_child")
  @NotNull
  private Long childId;

  @Column(name = "id_parent")
  @NotNull
  private Long parentId;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Type type;

  @Column(name = "report_type")
  @Enumerated(EnumType.STRING)
  private Report reportType;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Status status;
}