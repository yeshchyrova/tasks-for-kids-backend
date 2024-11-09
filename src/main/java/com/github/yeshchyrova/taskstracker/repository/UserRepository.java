package com.github.yeshchyrova.taskstracker.repository;

import com.github.yeshchyrova.taskstracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);
}
