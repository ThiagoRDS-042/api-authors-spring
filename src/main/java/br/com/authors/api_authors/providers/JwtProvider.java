package br.com.authors.api_authors.providers;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.authors.api_authors.providers.dtos.SignResponseDTO;

@Service
public class JwtProvider {
  @Value("${security.jwt.token.secret}")
  private String jwtSecret;

  public SignResponseDTO sign(String authorId) {
    var expiresIn = Instant.now().plus(Duration.ofHours(2));

    var algorithm = Algorithm.HMAC256(this.jwtSecret);

    var token = JWT.create()
        .withIssuer("api-authors")
        .withExpiresAt(expiresIn)
        .withSubject(authorId)
        .sign(algorithm);

    var response = new SignResponseDTO(token, expiresIn.toEpochMilli());

    return response;
  }

  public DecodedJWT validate(String token) {
    try {
      var algorithm = Algorithm.HMAC256(this.jwtSecret);

      var decodedToken = JWT.require(algorithm).build().verify(token);

      return decodedToken;
    } catch (JWTVerificationException exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
