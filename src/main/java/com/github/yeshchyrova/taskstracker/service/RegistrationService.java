package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.dtos.RegistrationRequestDto;
import com.github.yeshchyrova.taskstracker.model.Child;
import com.github.yeshchyrova.taskstracker.model.Family;
import com.github.yeshchyrova.taskstracker.model.Parent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {
  private final FamilyService familyService;
  private final ParentService parentService;
  private final ChildService childService;

  private final BCryptPasswordEncoder passwordEncoder;


  @Transactional
  public Map<String, Long> registerParentAndChild(RegistrationRequestDto request) {
    Family family = familyService.saveFamily();
    Long familyId = family.getId();

    Parent parent = new Parent();
    parent.setName(request.getParent().getName());
    parent.setEmail(request.getParent().getEmail());
    parent.setPassword(passwordEncoder.encode(request.getParent().getPassword()));
    parent.setFamilyId(familyId);
    parentService.saveParent(parent);

    String childPassword = generateRandomPassword();

    Child child = new Child();
    child.setName(request.getChild().getName());
    child.setEmail(request.getChild().getEmail());
    child.setPassword(passwordEncoder.encode(childPassword));
    child.setFamilyId(familyId);
    childService.saveChild(child);

//    Сравнение пароля при аутентификации: В процессе аутентификации при проверке пароля, вам нужно
//    использовать метод passwordEncoder.matches(rawPassword, encodedPassword), чтобы проверить
//    введенный пароль с хешированным.

//    sendEmail(request.getChild().getEmail(), childPassword);
    return Map.of("parentId", parent.getId(), "childId", child.getId());
  }

  public String generateRandomPassword() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

//  public void sendEmail(String email, String password) {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    EmailSender emailSender = new EmailSender(mailSender);
//
//    String subject = "Hello from Tasks Manager";
//    String emailContent = "<!DOCTYPE html>"
//            + "<html>"
//            + "<head>"
//            + "    <style>"
//            + "        .password {"
//            + "            font-size: 24px;"
//            + "            font-weight: bold;"
//            + "            color: #2c3e50;"
//            + "        }"
//            + "    </style>"
//            + "</head>"
//            + "<body>"
//            + "    <h2>Welcome to Our App!</h2>"
//            + "    <p>Dear User,</p>"
//            + "    <p>We’re excited to have you on board! Your parent has successfully registered" +
//            " you in Tasks Manager system.</p>"
//            + "    <p>To access your account, please use this email address and the password " +
//            "below:</p>"
//            + "    <p class=\"password\">" + password + "</p>"
//            + "    <p>Click the link below to log in:</p>"
//            + "    <p><a href=\"http://localhost:3000/login\" target=\"_blank\">Log In</a></p>"
//            + "    <p>If you encounter any issues, please don't hesitate to reach out to us.</p>"
//            + "    <p>Best regards,</p>"
//            + "    <p>Tasks Manager Team</p>"
//            + "</body>"
//            + "</html>";
//
//    try {
//      emailSender.sendEmail(email, subject, emailContent);
//      System.out.println("Email sent successfully.");
//    } catch (Exception e) {
//      System.out.println("Failed to send email. Error: " + e.getMessage());
//    }
//  }

}
