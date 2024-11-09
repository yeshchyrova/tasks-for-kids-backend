package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.model.Child;
import com.github.yeshchyrova.taskstracker.repository.ChildRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ChildService {
  private final ChildRepository childRepository;

  public Child getChildById(Long id) {
    return childRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Child not found"));
  }

  public Child getChildByEmail(String email) {
    return childRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Child not found"));
  }

  public Child saveChild(Child child) {
    return childRepository.save(child);
  }
}
