package com.github.yeshchyrova.taskstracker.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceForChildren implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return null;
  }
}
