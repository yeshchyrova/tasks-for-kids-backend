package com.github.yeshchyrova.taskstracker.dev;

import com.github.yeshchyrova.taskstracker.helper.EmailSender;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

// class is used only for testing and development
@RestController
@RequestMapping("/email")
public class EmailController {
  @PostMapping
  public void sendEmail(@RequestBody Map<String, String> request) throws MessagingException,
          UnsupportedEncodingException {
    String email = request.get("email");

    String password = UUID.randomUUID().toString().substring(0, 8);
    System.out.println("Email: " + email + "; Password: " + password);

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    EmailSender emailSender = new EmailSender(mailSender);

    String subject = "Hello from Tasks Manager";
    String emailContent = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "    <style>"
            + "        .password {"
            + "            font-size: 24px;"
            + "            font-weight: bold;"
            + "            color: #2c3e50;"
            + "        }"
            + "    </style>"
            + "</head>"
            + "<body>"
            + "    <h2>Welcome to Our App!</h2>"
            + "    <p>Dear User,</p>"
            + "    <p>We’re excited to have you on board! Your parent has successfully registered" +
            " you in Tasks Manager system.</p>"
            + "    <p>To access your account, please use this email address and the password " +
            "below:</p>"
            + "    <p class=\"password\">" + password + "</p>"
            + "    <p>Click the link below to log in:</p>"
            + "    <p><a href=\"http://localhost:3000/login\" target=\"_blank\">Log In</a></p>"
            + "    <p>If you encounter any issues, please don't hesitate to reach out to us.</p>"
            + "    <p>Best regards,</p>"
            + "    <p>Tasks Manager Team</p>"
            + "</body>"
            + "</html>";

    try {
      emailSender.sendEmail(email, subject, emailContent);
      System.out.println("Email sent successfully.");
    } catch (Exception e) {
      System.out.println("Failed to send email. Error: " + e.getMessage());
    }
  }



}
