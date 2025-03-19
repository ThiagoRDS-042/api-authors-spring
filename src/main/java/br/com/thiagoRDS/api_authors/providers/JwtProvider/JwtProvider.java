package br.com.thiagoRDS.api_authors.providers.JwtProvider;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.thiagoRDS.api_authors.providers.dtos.SignResponseDTO;

public abstract class JwtProvider {
  public abstract SignResponseDTO sign(String authorId);

  public abstract DecodedJWT validate(String token);
}
