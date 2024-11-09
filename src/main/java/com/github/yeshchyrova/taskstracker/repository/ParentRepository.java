package com.github.yeshchyrova.taskstracker.repository;

import com.github.yeshchyrova.taskstracker.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// класс обрабатывает логику доступа к данным == DAO Data Access Object
@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
//  Optional<Parent> findById(Long id);

  Optional<Parent> findByEmail(String email);

//  Optional<Parent> findByEmailIsIn(String name);
}