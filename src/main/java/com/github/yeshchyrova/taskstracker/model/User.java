package com.github.yeshchyrova.taskstracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String password;
  private String passwordHash;

//  @Column(name = "id_family", nullable = false)
//  private Long familyId;

}