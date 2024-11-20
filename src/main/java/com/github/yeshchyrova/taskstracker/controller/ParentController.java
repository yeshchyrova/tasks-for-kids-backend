//package com.github.yeshchyrova.taskstracker.controller;
//
//import com.github.yeshchyrova.taskstracker.model.Parent;
//import com.github.yeshchyrova.taskstracker.service.ParentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.lang.reflect.Array;
//import java.net.URI;
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class ParentController {
//  private final ParentService parentService;
//
////  @GetMapping("/parents")
////  в будущем подумать, нужна ли мне пагинация вообще. и если да, то нужно засовывать это в юрл в
////  реакте
////  public ResponseEntity<List<String>> getAllParents() {
////    return ResponseEntity.ok(Arrays.asList("parent1", "parent2"));
////  }
////  public ResponseEntity<Page<Parent>> getAllParents(
////          @RequestParam(value = "page", defaultValue = "0") int page,
////          @RequestParam(value = "size", defaultValue = "10") int size) {
////
////    Page<Parent> parents = parentService.getAllParents(page, size);
////    return ResponseEntity.ok().body(parents);
////  }
////  public ResponseEntity<List<Parent>> getAllParents() {
////    List<Parent> parents = parentService.getAllParents();
////    return ResponseEntity.ok().body(parents);
////  }
//
//  @GetMapping("/{id}")
//  public ResponseEntity<Parent> getParentById(@PathVariable Long id) {
//    Parent parent = parentService.getParentById(id);
//    return ResponseEntity.ok().body(parent);
//  }
//
////  @PostMapping
////  public ResponseEntity<Parent> createParent(@RequestBody Parent parent) {
////    Parent newParent = parentService.createParent(parent);
////    return ResponseEntity.created(URI.create("/parents/" + newParent.getId())).body(newParent);
////  }
//}
