package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.dtos.CompletedTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.FullInfoTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.NewTaskDto;
import com.github.yeshchyrova.taskstracker.dtos.TaskWithNamesDto;
import com.github.yeshchyrova.taskstracker.dtos.stats.TaskTypeSpentTimeDto;
import com.github.yeshchyrova.taskstracker.entity.CompletedTask;
import com.github.yeshchyrova.taskstracker.entity.Task;
import com.github.yeshchyrova.taskstracker.enums.Mood;
import com.github.yeshchyrova.taskstracker.enums.Status;
import com.github.yeshchyrova.taskstracker.enums.Type;
import com.github.yeshchyrova.taskstracker.exceptions.AppException;
import com.github.yeshchyrova.taskstracker.repositories.CompletedTaskRepository;
import com.github.yeshchyrova.taskstracker.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

  public FullInfoTaskDto getTaskById(Long id) {
    Optional<CompletedTask> completedTask = completedTaskRepository.findById(id);
    TaskWithNamesDto task = taskRepository.findTaskWithNamesById(id)
            .orElseThrow(() -> new AppException("Task not found", HttpStatus.NOT_FOUND));
    FullInfoTaskDto fullInfoTaskDto = new FullInfoTaskDto(task);
    completedTask.ifPresent(fullInfoTaskDto::setCompletedTask);
    return fullInfoTaskDto;
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

  @Transactional
  public FullInfoTaskDto completeTask(CompletedTaskDto completedTask) {
    CompletedTask complTask = new CompletedTask();

    complTask.setId(completedTask.getId());
    complTask.setPhotoReport(completedTask.getPhotoReport());
    complTask.setTextReport(completedTask.getTextReport());
    complTask.setReportTime(LocalDateTime.parse(completedTask.getReportTime()));
    complTask.setSpentTime(completedTask.getSpentTime());
    complTask.setMood(completedTask.getMood());

    CompletedTask savedTask = completedTaskRepository.save(complTask);

    taskRepository.updateStatusById(completedTask.getId());
    FullInfoTaskDto fullInfoTaskDto = new FullInfoTaskDto(
            taskRepository.findTaskWithNamesById(completedTask.getId())
                    .orElse(new TaskWithNamesDto())
    );
    fullInfoTaskDto.setCompletedTask(savedTask);
    return fullInfoTaskDto;
  }

  public List<TaskTypeSpentTimeDto> getSpentTimeByTaskType(Long childId) {
    List<Map<String, Object>> results = taskRepository.findTaskTypeAndSpentTimeByChildId(childId);
    return results.stream()
            .map(row -> new TaskTypeSpentTimeDto(
                    (String) row.get("taskType"),
                    Duration.ofSeconds(((Number) row.get("spentTotal")).longValue()).toString()
            ))
            .toList();
  }

  public List<Map<String, Object>> getCompletedTasksByMood(Long childId, String mood) {
    return taskRepository.getCompletedTasksByMood(childId, mood.toUpperCase());
  }

  public List<Map<String, Object>> getExpiredTasks(Long childId) {
    return taskRepository.getExpiredTasks(childId);
  }
}
