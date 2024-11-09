package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.dtos.ParentRegistrationRequest;
import com.github.yeshchyrova.taskstracker.model.Child;
import com.github.yeshchyrova.taskstracker.model.Family;
import com.github.yeshchyrova.taskstracker.model.Parent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {
  private final FamilyService familyService;
  private final ParentService parentService;
  private final ChildService childService;
//  private final EmailService emailService;


  @Transactional
  public Map<String, Long> registerParentAndChild(ParentRegistrationRequest request) {
    Family family = familyService.saveFamily();
    Long familyId = family.getId();

    Parent parent = new Parent();
    parent.setName(request.getParent().getName());
    parent.setEmail(request.getParent().getEmail());
    parent.setPassword(request.getParent().getPassword());
    parent.setFamilyId(familyId);
    parentService.saveParent(parent);

    String childPassword = generateRandomPassword();

    Child child = new Child();
    child.setName(request.getChild().getName());
    child.setEmail(request.getChild().getEmail());
    child.setPassword(childPassword);
    child.setFamilyId(familyId);
    childService.saveChild(child);

    // send password to child

    return Map.of("parentId", parent.getId(), "childId", child.getId());
  }

  public String generateRandomPassword() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

}
