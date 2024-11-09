package com.github.yeshchyrova.taskstracker.config;

import com.github.yeshchyrova.taskstracker.model.Child;
import com.github.yeshchyrova.taskstracker.model.Parent;
import com.github.yeshchyrova.taskstracker.service.ChildService;
import com.github.yeshchyrova.taskstracker.service.ParentService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class AuthenticationSuccessHandlerClass implements AuthenticationSuccessHandler {

  private ParentService parentService;
  private ChildService childService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      FilterChain chain, Authentication authentication)
          throws IOException, ServletException {
    String targetUrl = determiveTargetUrl(authentication);
    if (response.isCommitted()) {
      return;
    }

    response.sendRedirect(targetUrl);
  }

  private String determiveTargetUrl(Authentication authentication) {
    String user = getUser(authentication);
    System.out.println(user);
//    Long id = authentication.getPrincipal().getId();
//    Long id = user.ge
    if ("PARENT".equals(user)) {
      return "/parent/home";
    } else if ("CHILD".equals(user)) {
      return "/child/home";
    } else {
      return "/";
    }
  }

  private String getUser(Authentication authentication) {
    return "";
//    String temp = null;
//    String email = authentication.ge();
//
//    Parent parent = parentService.getParentByEmail(email);
//    Child child = childService.getChildByEmail(email);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication)
          throws IOException, ServletException {

  }
}
