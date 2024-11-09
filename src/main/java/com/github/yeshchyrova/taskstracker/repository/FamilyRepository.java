package com.github.yeshchyrova.taskstracker.repository;

import com.github.yeshchyrova.taskstracker.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Long> {
}
