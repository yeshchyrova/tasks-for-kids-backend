package com.github.yeshchyrova.taskstracker.entity;

import com.github.yeshchyrova.taskstracker.enums.Mood;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "completed_tasks")
public class CompletedTask {
  @Id
  private Long id;

  @Column(name = "photo_report")
  private String photoReport;

  @Column(name = "text_report")
  @Size(max = 500)
  private String textReport;

  @PastOrPresent
  @Column(name = "report_time")
  private LocalDateTime reportTime;

  @NotNull
  @Column(name = "spent_time", columnDefinition = "interval")
  private Duration spentTime;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Mood mood;

  public void setSpentTime(String spentTime) {
    this.spentTime = Duration.parse(spentTime);
  }
}
