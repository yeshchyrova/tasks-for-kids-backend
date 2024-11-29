package com.github.yeshchyrova.taskstracker.repositories;

import com.github.yeshchyrova.taskstracker.enums.Role;
import com.github.yeshchyrova.taskstracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByLogin(String login);

  List<User> findAllByFamilyIdAndRole(Long familyId, Role role);
}
