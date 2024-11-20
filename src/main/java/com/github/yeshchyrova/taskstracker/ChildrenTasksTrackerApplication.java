package com.github.yeshchyrova.taskstracker;

//import com.github.yeshchyrova.taskstracker.model.Parent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import java.util.Optional;

@SpringBootApplication
public class ChildrenTasksTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChildrenTasksTrackerApplication.class, args);
    System.out.println("Server is running...");
	}
}
