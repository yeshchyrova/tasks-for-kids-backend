package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.dtos.FullInfoTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.NewTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.TaskWithNamesDto;
import com.github.yeshchyrova.taskstracker.entity.CompletedTask;
import com.github.yeshchyrova.taskstracker.entity.Task;
import com.github.yeshchyrova.taskstracker.enums.Status;
import com.github.yeshchyrova.taskstracker.exceptions.AppException;
import com.github.yeshchyrova.taskstracker.repositories.CompletedTaskRepository;
import com.github.yeshchyrova.taskstracker.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskService {
  private final TaskRepository taskRepository;
  private final CompletedTaskRepository completedTaskRepository;

  public List<TaskWithNamesDto> getTasksByChildId(Long childId) {
    List<TaskWithNamesDto> tasks = taskRepository.findAllByChildId(childId);
    if (tasks.isEmpty()) throw new AppException("Tasks not found", HttpStatus.NOT_FOUND);
    return tasks;
  }

  public Object getTaskById(Long id) {
    Optional<CompletedTask> completedTask = completedTaskRepository.findById(id);
    TaskWithNamesDto task = taskRepository.findTaskWithNamesById(id)
            .orElseThrow(() -> new AppException("Task not found", HttpStatus.NOT_FOUND));
    if (completedTask.isPresent()) return new FullInfoTaskDto(task, completedTask.get());
    return task;
  }

  public Object addTask(NewTaskDto newTask) {
    Task task = new Task();
    // проверить существование айдишников, проверить с одной ли они семьи, проверить роли
    task.setTitle(newTask.getTitle());
    task.setDescription(newTask.getDescription());
    String customDeadline = newTask.getDeadline();
    LocalDateTime ldt = LocalDateTime.parse(customDeadline);
    task.setDeadline(ldt);
    task.setChildId(newTask.getChildId());
    task.setParentId(newTask.getParentId());
    task.setTaskType(newTask.getTaskType());
    task.setReportType(newTask.getReportType());
    task.setStatus(Status.TODO);
    Task savedTask = taskRepository.save(task);

    return getTaskById(savedTask.getId());
  }

  public void confirmTask(Long id) {
    Optional<Task> task = taskRepository.findById(id);
    if (task.isEmpty()) throw new AppException("Task not found", HttpStatus.NOT_FOUND);
    task.get().setStatus(Status.COMPLETED);
    taskRepository.save(task.get());
  }

//  public String uploadReport(Long id, MultipartFile file) {
//    CompletedTask completedTask = completedTaskRepository.findById(id)
//            .orElseThrow(() -> new AppException("Task not found", HttpStatus.NOT_FOUND));
//    completedTask.setPhotoReport(reportUrl);
//    completedTaskRepository.save(completedTask);
//    return reportUrl;
//  }
}
