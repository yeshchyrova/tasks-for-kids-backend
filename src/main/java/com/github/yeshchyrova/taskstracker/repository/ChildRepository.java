package com.github.yeshchyrova.taskstracker.repository;

import com.github.yeshchyrova.taskstracker.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
//  Optional<Child> findById(Long id);
  Optional<Child> findByEmail(String email);
}
