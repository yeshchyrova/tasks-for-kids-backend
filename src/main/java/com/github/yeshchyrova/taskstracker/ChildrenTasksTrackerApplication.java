package com.github.yeshchyrova.taskstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChildrenTasksTrackerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChildrenTasksTrackerApplication.class, args);
    System.out.println("Server is running...");
  }
}
