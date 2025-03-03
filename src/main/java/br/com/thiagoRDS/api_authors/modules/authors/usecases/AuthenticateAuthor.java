package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthenticateAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthenticateAuthorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.InvalidCredentialsException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.providers.JwtProvider;
import br.com.thiagoRDS.api_authors.providers.dtos.SignResponseDTO;

@Service
public class AuthenticateAuthor {
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthorsRepository authorsRepository;

  public AuthenticateAuthor(AuthorsRepository authorsRepository, JwtProvider jwtProvider,
      PasswordEncoder passwordEncoder) {
    this.jwtProvider = jwtProvider;
    this.passwordEncoder = passwordEncoder;
    this.authorsRepository = authorsRepository;
  }

  public AuthenticateAuthorResponseDTO execute(AuthenticateAuthorDTO data) {
    Author author = this.authorsRepository.findByEmail(data.email()).orElseThrow(() -> {
      throw new InvalidCredentialsException();
    });

    boolean passwordDoesMatches = this.passwordEncoder.matches(data.password(), author.getPassword());

    if (!passwordDoesMatches) {
      throw new InvalidCredentialsException();
    }

    SignResponseDTO signToken = this.jwtProvider.sign(author.getId().toString());

    AuthenticateAuthorResponseDTO response = new AuthenticateAuthorResponseDTO(signToken.token(),
        signToken.expiresIn());

    return response;
  }
}
