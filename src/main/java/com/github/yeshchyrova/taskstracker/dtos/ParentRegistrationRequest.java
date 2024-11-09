package com.github.yeshchyrova.taskstracker.dtos;

//import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentRegistrationRequest {
  private ParentDto parent;
  private ChildDto child;
}
