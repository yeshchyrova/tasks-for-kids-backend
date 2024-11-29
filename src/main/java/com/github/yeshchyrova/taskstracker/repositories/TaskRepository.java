package com.github.yeshchyrova.taskstracker.repositories;

import com.github.yeshchyrova.taskstracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findAllByChildId (Long childId);
}
