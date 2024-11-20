package com.github.yeshchyrova.taskstracker.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Enumeration;

public enum Role implements GrantedAuthority {
  PARENT, CHILD;

  @Override
  public String getAuthority() {
    return this.name();
  }
}
