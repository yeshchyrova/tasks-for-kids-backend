package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.dtos.FullInfoTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.NewTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.TaskWithNamesDto;
import com.github.yeshchyrova.taskstracker.entity.Task;
import com.github.yeshchyrova.taskstracker.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  public ResponseEntity addTask(@RequestBody NewTaskDto task) {
    taskService.addTask(task);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/confirm/{taskId}")
  public ResponseEntity confirmTask(@PathVariable Long taskId) {
    taskService.confirmTask(taskId);
    return ResponseEntity.ok().build();
  }

}
