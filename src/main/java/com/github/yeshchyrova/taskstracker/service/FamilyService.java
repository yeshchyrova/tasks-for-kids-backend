package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.constant.Role;
import com.github.yeshchyrova.taskstracker.dtos.CredentialsDto;
import com.github.yeshchyrova.taskstracker.dtos.UserRegistrationRequest;
import com.github.yeshchyrova.taskstracker.model.Child;
import com.github.yeshchyrova.taskstracker.model.Family;
import com.github.yeshchyrova.taskstracker.model.Parent;
import com.github.yeshchyrova.taskstracker.repository.ChildRepository;
import com.github.yeshchyrova.taskstracker.repository.FamilyRepository;
import com.github.yeshchyrova.taskstracker.repository.ParentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyService {
  private final FamilyRepository familyRepository;
  private final ParentRepository parentRepository;
  private final ChildRepository childRepository;
//  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void registerFamily(UserRegistrationRequest request) {
    Family family = familyRepository.save(new Family());
    Long familyId = family.getId();

    Role userRole = request.getUser().getRole();

    if(Role.PARENT.equals(userRole)) {
//      user - parent
      Parent parent = new Parent();
      parent.setName(request.getUser().getName());
      parent.setEmail(request.getUser().getEmail());
      parent.setPassword(request.getUser().getPassword());
//      parent.setPassword(passwordEncoder.encode(request.getUser().getPassword()));
      parent.setFamilyId(familyId);
      parentRepository.save(parent);

      Child child = new Child();
      child.setName(request.getMember().getName());
      child.setEmail(request.getMember().getEmail());
      child.setFamilyId(familyId);
      childRepository.save(child);
    } else if (Role.CHILD.equals(userRole)) {
//      user - child
      Child child = new Child();
      child.setName(request.getUser().getName());
      child.setEmail(request.getUser().getEmail());
      child.setPassword(request.getUser().getPassword());
      child.setFamilyId(familyId);
      childRepository.save(child);

      Parent parent = new Parent();
      parent.setName(request.getMember().getName());
      parent.setEmail(request.getMember().getEmail());
      parent.setFamilyId(familyId);
      parentRepository.save(parent);
    }
  }

//


}
