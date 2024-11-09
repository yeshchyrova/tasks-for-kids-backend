package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.dtos.ParentRegistrationRequest;
import com.github.yeshchyrova.taskstracker.model.Family;
import com.github.yeshchyrova.taskstracker.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyService {
  private final FamilyRepository familyRepository;

  public Family saveFamily() {
    return familyRepository.save(new Family());
  }
}