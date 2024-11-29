package com.github.yeshchyrova.taskstracker.entity;

import com.github.yeshchyrova.taskstracker.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 50)
  @NotNull
  private String name;

  @Column(name = "email", unique = true)
  @Size(max = 100)
  @Email
  @NotNull
  private String login;

  @Size(max = 100)
  @NotNull
  private String password;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Role role;

  @Column(name = "id_family")
  @NotNull
  private Long familyId;
}
