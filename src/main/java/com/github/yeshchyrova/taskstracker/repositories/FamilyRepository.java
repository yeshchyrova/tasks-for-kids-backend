package com.github.yeshchyrova.taskstracker.repositories;

import com.github.yeshchyrova.taskstracker.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Long> {
}
