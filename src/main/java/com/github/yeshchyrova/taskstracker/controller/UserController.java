package com.github.yeshchyrova.taskstracker.controller;

import com.github.yeshchyrova.taskstracker.dtos.ChildByFamilyDto;
import com.github.yeshchyrova.taskstracker.dtos.NewMemberDto;
import com.github.yeshchyrova.taskstracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @GetMapping("/{familyId}/children")
  public ResponseEntity<List<ChildByFamilyDto>> getChildrenByFamilyId(@PathVariable Long familyId) {
    return ResponseEntity.ok(userService.findAllChildrenByFamilyId(familyId));
  }

  @PostMapping("/member")
  public void addFamilyMember(@RequestBody NewMemberDto memberDto) {
    userService.addFamilyMember(memberDto);
  }
}
