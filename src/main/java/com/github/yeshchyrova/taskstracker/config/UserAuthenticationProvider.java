package com.github.yeshchyrova.taskstracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import io.github.cdimascio.dotenv.Dotenv;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yeshchyrova.taskstracker.dtos.UserDto;
import com.github.yeshchyrova.taskstracker.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

  @Value("${JWT_SECRET_KEY:506ff37cc612224b61cec9435d611bf30dfa6cd86e50d89548d7781164b6958dc48b15d980149bd3100c7e0703ded2174cd08dec948ebc80ad28134a2154f948}")
  private String secretKey;

  private final UserService userService;

  @PostConstruct
  protected void init() {
//    Dotenv dotenv = Dotenv.load();
//    this.secretKey = dotenv.get("JWT_SECRET_KEY");

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
            .sign(algorithm);
  }

  public Authentication validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    JWTVerifier verifier = JWT.require(algorithm).build();

    DecodedJWT decoded = verifier.verify(token);

    UserDto user = userService.findByLogin(decoded.getSubject());

    return new UsernamePasswordAuthenticationToken(user, null,
                                                   Collections.singletonList(user.getRole()));
  }

  public UserDto getCurrentUser(String token) {
    DecodedJWT decoded = JWT.decode(token);
    return userService.findByLogin(decoded.getSubject());
  }

}
