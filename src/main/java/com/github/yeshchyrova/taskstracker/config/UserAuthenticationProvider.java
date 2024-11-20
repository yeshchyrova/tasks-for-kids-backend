package com.github.yeshchyrova.taskstracker.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yeshchyrova.taskstracker.dtos.UserDto;
import com.github.yeshchyrova.taskstracker.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

//  add secret key to application.properties
  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  private final UserService userService;

  @PostConstruct
  protected void init() {
    // this is to avoid having the raw secret key available in the JVM
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(UserDto user) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + 100L * 24 * 60 * 60 * 1000); // 100 days

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    return JWT.create()
            .withSubject(user.getLogin())
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withClaim("role", user.getRole().name())
            .withClaim("id", user.getId())
            .withClaim("family-id", user.getFamilyId())
            .sign(algorithm);
  }

  public Authentication validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    JWTVerifier verifier = JWT.require(algorithm)
            .build();

    DecodedJWT decoded = verifier.verify(token);

    UserDto user = userService.findByLogin(decoded.getSubject());

    return new UsernamePasswordAuthenticationToken(user, null,
                                                   Collections.singletonList(user.getRole()));
//    return new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(user.getRole()));
  }

}
