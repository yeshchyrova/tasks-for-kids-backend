package com.github.yeshchyrova.taskstracker.repository;

import com.github.yeshchyrova.taskstracker.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, String> {
  @Override
  Optional<Parent> findById(String id);
}

