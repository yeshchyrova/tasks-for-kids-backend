package com.github.yeshchyrova.taskstracker.repositories;

import com.github.yeshchyrova.taskstracker.entity.CompletedTask;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompletedTaskRepository extends JpaRepository<CompletedTask, Long> {
  @NotNull
  Optional<CompletedTask> findById(@NotNull Long id);
}
