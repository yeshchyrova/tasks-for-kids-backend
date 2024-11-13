package com.github.yeshchyrova.taskstracker.config;

import com.github.yeshchyrova.taskstracker.model.Child;
import com.github.yeshchyrova.taskstracker.model.Parent;
import com.github.yeshchyrova.taskstracker.repository.ChildRepository;
import com.github.yeshchyrova.taskstracker.repository.ParentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final ParentRepository parentRepository;
  private final ChildRepository childRepository;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    throw new UnsupportedOperationException("Method not used. Use loadUserByEmailAndRole instead.");
  }

  public UserDetails loadUserByEmailAndRole(String email, String role)
          throws UsernameNotFoundException {
    if ("PARENT".equals(role)) {
      Parent parent =
              parentRepository.findByEmail(email)
                      .orElseThrow(() -> new UsernameNotFoundException("Parent not found"));
      return new CustomUserDetails(parent.getEmail(), parent.getPassword(),
                                   Collections.singletonList(new SimpleGrantedAuthority(
                                           "ROLE_PARENT")));
    } else if ("CHILD".equals(role)) {
      Child child =
              childRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                      "Child not found"));
      return new CustomUserDetails(child.getEmail(), child.getPassword(),
                                   Collections.singletonList(
                                           new SimpleGrantedAuthority("ROLE_CHILD")));
    } else throw new UsernameNotFoundException("Role not recognized");
  }
}
