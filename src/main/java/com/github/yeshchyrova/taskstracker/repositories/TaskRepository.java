package com.github.yeshchyrova.taskstracker.repositories;

import com.github.yeshchyrova.taskstracker.dtos.TaskWithNamesDto;
import com.github.yeshchyrova.taskstracker.entity.Task;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  @Query("""
              SELECT new com.github.yeshchyrova.taskstracker.dtos.TaskWithNamesDto(
                  t.id,
                              t.title,
                              t.description,
                              t.deadline,
                              t.childId,
                              t.parentId,
                              t.taskType,
                              t.reportType,
                              t.status,
                  (SELECT u.name FROM User u WHERE u.id = t.parentId),
                  (SELECT u.name FROM User u WHERE u.id = t.childId)
              )
              FROM Task t
              WHERE t.childId = :idChild
          """)
  List<TaskWithNamesDto> findAllByChildId(@Param("idChild") Long childId);

  @NotNull
  @Query("""
              SELECT new com.github.yeshchyrova.taskstracker.dtos.TaskWithNamesDto(
                  t.id,
                              t.title,
                              t.description,
                              t.deadline,
                              t.childId,
                              t.parentId,
                              t.taskType,
                              t.reportType,
                              t.status,
                  (SELECT u.name FROM User u WHERE u.id = t.parentId),
                  (SELECT u.name FROM User u WHERE u.id = t.childId)
              )
              FROM Task t
              WHERE t.id = :taskId
          """)
  Optional<TaskWithNamesDto> findTaskWithNamesById(@Param("taskId") Long taskId);

  @Modifying
  @Query("update Task t set t.status='CONFIRM' where t.id=:taskId")
  void updateStatusById(@Param("taskId") Long taskId);

}
