package com.github.yeshchyrova.taskstracker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()));
    configuration.setAllowedHeaders(Arrays.asList(
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.CONTENT_TYPE));
    configuration.setExposedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  private final UserAuthenticationProvider userAuthenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors(c -> c.configurationSource(corsConfigurationSource()))
            .exceptionHandling(exp -> exp.authenticationEntryPoint(new HttpStatusEntryPoint(
                    HttpStatus.BAD_REQUEST)))
            .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider),
                             BasicAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                    .anyRequest().authenticated())
    ;
    return http.build();
  }
}


//@Configuration
//@EnableWebSecurity
//@AllArgsConstructor
//public class SecurityConfig {
//  private final CustomUserDetailsService userDetailsService;
//
//  @Bean
//  public BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http
//            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(authorize -> authorize
//                    .requestMatchers("/login", "/email", "/register", "index", "/css/*",
//                                     "/js/*").permitAll()
//                    .anyRequest().authenticated())
//            .httpBasic(Customizer.withDefaults())
//            .formLogin(login -> login.usernameParameter("email"))
//            .rememberMe(token -> token
//                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(100))
//                    .rememberMeParameter("remember-me"))
//            .logout(logout -> logout
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/login")
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID", "remember-me"))
////            .addFilterBefore(customAuthFilter(
////                                     authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))),
////                             UsernamePasswordAuthenticationFilter.class)
//            .build();
//  }
//
//  @Bean
//  public AuthenticationManager authenticationManager(
//          AuthenticationConfiguration authenticationConfiguration) throws Exception {
//    return authenticationConfiguration.getAuthenticationManager();
//  }
//
//  @Bean
//  public CustomAuthFilter customAuthFilter(AuthenticationManager authenticationManager) {
//    return new CustomAuthFilter(authenticationManager);
//  }
//
//  @Bean
//  public AuthenticationProvider authenticationProvider() {
//    // провайдер загружает данные о пользователе используя методы UserDetailsService
//    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//    provider.setUserDetailsService(userDetailsService);
//    provider.setPasswordEncoder(passwordEncoder());
//    return provider;
//  }
//}

