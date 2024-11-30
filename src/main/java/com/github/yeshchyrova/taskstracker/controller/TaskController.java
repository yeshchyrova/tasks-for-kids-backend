package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.entity.Task;
import com.github.yeshchyrova.taskstracker.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TaskController {
  private final TaskService taskService;
  @GetMapping("/{childId}/tasks")
  public ResponseEntity<List<Task>> getAllTasksByChildId(@PathVariable("childId") Long id) {
    return ResponseEntity.ok(taskService.getTasksByChildId(id));
  }
}
