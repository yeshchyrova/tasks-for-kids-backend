package com.github.yeshchyrova.taskstracker.repositories;

import com.github.yeshchyrova.taskstracker.dtos.TaskWithNamesDto;
import com.github.yeshchyrova.taskstracker.dtos.stats.TaskTypeSpentTimeDto;
import com.github.yeshchyrova.taskstracker.entity.Task;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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
  @Transactional
  @Query("update Task t set t.status='CONFIRM' where t.id=:taskId")
  void updateStatusById(@Param("taskId") Long taskId);


  @Query(value = """
          SELECT t.task_type AS taskType, EXTRACT(EPOCH FROM SUM(ct.spent_time)) AS spentTotal\s
          FROM tasks t\s
          INNER JOIN completed_tasks ct ON t.id = ct.id
          WHERE t.id_child = :childId
          GROUP BY t.task_type
         \s""", nativeQuery = true)
  List<Map<String, Object>> findTaskTypeAndSpentTimeByChildId(@Param("childId") Long childId);
}
