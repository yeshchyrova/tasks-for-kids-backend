package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.entity.Task;
import com.github.yeshchyrova.taskstracker.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {
  private final TaskRepository taskRepository;

  public List<Task> getTasksByChildId(Long childId) {
    return taskRepository.findAllByChildId(childId);
  }
}
