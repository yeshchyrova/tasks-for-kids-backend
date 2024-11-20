package com.github.yeshchyrova.taskstracker.repositories;

import com.github.yeshchyrova.taskstracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByLogin(String login);
}
