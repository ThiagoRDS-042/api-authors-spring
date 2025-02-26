package br.com.authors.api_authors.modules.authors.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.dtos.AuthenticateAuthorDTO;
import br.com.authors.api_authors.modules.authors.dtos.AuthenticateAuthorResponseDTO;
import br.com.authors.api_authors.modules.authors.exceptions.InvalidCredentials;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.authors.api_authors.providers.JwtProvider;

@Service
public class AuthenticateAuthor {
  private JwtProvider jwtProvider;

  private PasswordEncoder passwordEncoder;

  private AuthorsRepository authorsRepository;

  public AuthenticateAuthor(AuthorsRepository authorsRepository, JwtProvider jwtProvider,
      PasswordEncoder passwordEncoder) {
    this.authorsRepository = authorsRepository;
    this.jwtProvider = jwtProvider;
    this.passwordEncoder = passwordEncoder;
  }

  public AuthenticateAuthorResponseDTO execute(AuthenticateAuthorDTO data) {
    var author = this.authorsRepository.findByEmail(data.email()).orElseThrow(() -> {
      throw new InvalidCredentials();
    });

    var passwordDoesMatches = this.passwordEncoder.matches(data.password(), author.getPassword());

    if (!passwordDoesMatches) {
      throw new InvalidCredentials();
    }

    var signToken = this.jwtProvider.sign(author.getId().toString());

    var response = new AuthenticateAuthorResponseDTO(signToken.token(), signToken.expiresIn());

    return response;
  }
}
