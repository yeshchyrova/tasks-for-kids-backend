package com.github.yeshchyrova.taskstracker.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "parents")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Parent {
  @Id
  @UuidGenerator
  @Column(name = "id", unique = true, updatable = false)
  private String id;
  private String name;
  private String email;
  private String password;
}
