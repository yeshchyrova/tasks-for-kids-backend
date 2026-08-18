package com.github.yeshchyrova.taskstracker.helpers.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RemoteEmailServiceImpl implements EmailService {

  private final RestClient restClient;

  public RemoteEmailServiceImpl(@Value("${notification-service.url}") String notificationServiceUrl) {
    this.restClient = RestClient.builder().baseUrl(notificationServiceUrl).build();
  }

  @Override
  public void sendSimpleMail(EmailDetails details) {
    restClient.post()
            .uri("/notifications/email")
            .contentType(MediaType.APPLICATION_JSON)
            .body(details)
            .retrieve()
            .toBodilessEntity();
  }
}
