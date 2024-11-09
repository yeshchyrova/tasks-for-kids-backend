package com.github.yeshchyrova.taskstracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "children")
public class Child {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @Column(nullable = false)
//  @Size(max = 100)
  private String name;

//  @Column(nullable = false)
//  @Size(max = 100)
  private String email;

//  @Column(nullable = false)
//  @Size(max = 100)
  private String password;

  @Column(name = "id_family", nullable = false)
  private Long familyId;
}
