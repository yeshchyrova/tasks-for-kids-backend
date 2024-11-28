package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.dtos.ChildByFamilyDto;
import com.github.yeshchyrova.taskstracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

  private UserService userService;

//  @PreAuthorize("hasAuthority('PARENT')")
  @GetMapping("/children/{familyId}")
  public ResponseEntity<List<ChildByFamilyDto>> getChildrenByFamilyId(@PathVariable Long familyId) {
    return ResponseEntity.ok(userService.findAllChildrenByFamilyId(familyId));
  }
}
