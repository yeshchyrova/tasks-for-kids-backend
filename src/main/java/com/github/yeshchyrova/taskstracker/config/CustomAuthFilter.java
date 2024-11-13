package com.github.yeshchyrova.taskstracker.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
  private final ObjectMapper mapper = new ObjectMapper();

  public CustomAuthFilter(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response)
          throws AuthenticationException {

    try {
      JsonNode jsonNode = mapper.readTree(request.getInputStream());
      String email = jsonNode.get("email").asText();
      String password = jsonNode.get("password").asText();
      String role = jsonNode.get("role").asText();

      UsernamePasswordAuthenticationToken authenticationRequest =
              new UsernamePasswordAuthenticationToken(
                      email, password);
      authenticationRequest.setDetails(role);
      return this.getAuthenticationManager().authenticate(authenticationRequest);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                          FilterChain chain, Authentication authResult)
          throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
  }


}
