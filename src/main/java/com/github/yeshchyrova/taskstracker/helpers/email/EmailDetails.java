package com.github.yeshchyrova.taskstracker.helpers.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDetails {
  private String recipient;
  private String msgBody;
  private String subject;
}
