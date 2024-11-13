package com.github.yeshchyrova.taskstracker.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// обьект пользователя, который загружается из бд (при попытке логина), которого мы нашли по имейлу
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
  private String email;
  private String password;
  // authorities мы устанавливаем самостоятельно при вызове конструктора CustomUserDetails в
  // CustomUserDetailsService. поэтому в authorities уже лежит коллекция из одной строки,
  // обозначающей роль
  private Collection<? extends GrantedAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
