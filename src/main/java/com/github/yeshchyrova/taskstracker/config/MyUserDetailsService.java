package com.github.yeshchyrova.taskstracker.config;

import com.github.yeshchyrova.taskstracker.model.User;
import com.github.yeshchyrova.taskstracker.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public MyUserDetailsService(
          UserRepository userRepository) { this.userRepository = userRepository; }


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UsernameNotFoundException("User does not exist in the database");
    }
    return user;
  }
}
