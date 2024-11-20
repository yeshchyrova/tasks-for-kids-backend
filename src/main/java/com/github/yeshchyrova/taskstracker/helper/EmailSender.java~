package com.github.yeshchyrova.taskstracker.helper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

public class EmailSender {
  private JavaMailSender mailSender;

  @Value("${SPRING_MAIL_USERNAME}")
  private String fromEmail;

  public EmailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }


  public void sendEmail(String email, String subject, String content) throws MessagingException,
          UnsupportedEncodingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    helper.setFrom(fromEmail, "Tasks Manager");
    helper.setTo(email);
    helper.setSubject(subject);
    helper.setText(content, true);
    mailSender.send(message);
  }
}
