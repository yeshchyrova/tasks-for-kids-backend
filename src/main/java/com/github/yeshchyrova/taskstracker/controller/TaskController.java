package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.dtos.CompletedTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.FullInfoTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.NewTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.TaskWithNamesDto;
import com.github.yeshchyrova.taskstracker.dtos.stats.TaskTypeSpentTimeDto;
import com.github.yeshchyrova.taskstracker.enums.Mood;
import com.github.yeshchyrova.taskstracker.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @GetMapping("/{childId}/tasks")
  public ResponseEntity<List<TaskWithNamesDto>> getAllTasksByChildId(
          @PathVariable("childId") Long id) {
    return ResponseEntity.ok(taskService.getTasksByChildId(id));
  }

  @GetMapping("/tasks/{taskId}")
  public ResponseEntity<?> getTaskById(@PathVariable("taskId") Long id) {
    return ResponseEntity.ok(taskService.getTaskById(id));
  }

  @PostMapping("/tasks")
  public ResponseEntity<?> addTask(@RequestBody NewTaskDto task) {
    Object savedTask = taskService.addTask(task);
    return ResponseEntity.ok(savedTask);
  }

  @PostMapping("/complete")
  public ResponseEntity<FullInfoTaskDto> completeTask(@RequestBody CompletedTaskDto completedTask) {
    FullInfoTaskDto fullTask = taskService.completeTask(completedTask);
    return ResponseEntity.ok(fullTask);
  }

  @PatchMapping("/confirm/{taskId}")
  public ResponseEntity confirmTask(@PathVariable Long taskId) {
    taskService.confirmTask(taskId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("statistics/{childId}/spent-time-task-type")
  public ResponseEntity<List<TaskTypeSpentTimeDto>> getSpentTimeOnTaskType(
          @PathVariable Long childId) {
    List<TaskTypeSpentTimeDto> spentTimeData = taskService.getSpentTimeByTaskType(childId);
    return ResponseEntity.ok(spentTimeData);
  }

  @GetMapping("statistics/{childId}/tasks-by-mood/{mood}")
  public ResponseEntity<List<Map<String, Object>>> getCompletedTasksByMood(@PathVariable Long childId,
                                                   @PathVariable String mood) {
    List<Map<String, Object>> res = taskService.getCompletedTasksByMood(childId, mood);
    return ResponseEntity.ok(res);
  }

  @GetMapping("statistics/{childId}/expired-tasks")
  public ResponseEntity<List<Map<String, Object>>> getExpiredTasks(@PathVariable Long childId) {
    List<Map<String, Object>> res = taskService.getExpiredTasks(childId);
    return ResponseEntity.ok(res);
  }
}
