package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthenticateAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthenticateAuthorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.InvalidCredentialsException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.providers.JwtProvider;
import br.com.thiagoRDS.api_authors.providers.dtos.SignResponseDTO;

@ExtendWith(MockitoExtension.class)
public class AuthenticateAuthorTest {
  @InjectMocks
  private AuthenticateAuthor authenticateAuthor;

  @Mock
  private JwtProvider jwtProvider;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthorsRepository authorsRepository;

  @Test
  @DisplayName("Should be able to authenticate a author")
  public void authenticateAuthor() {
    Author author = MakeAuthor.AUTHOR.clone();

    AuthenticateAuthorDTO authenticateAuthor = new AuthenticateAuthorDTO(
        author.getEmail(),
        author.getPassword());

    Long expiresIn = Instant.now().plus(Duration.ofHours(2)).toEpochMilli();

    SignResponseDTO signResponse = new SignResponseDTO("token", expiresIn);

    when(this.authorsRepository.findByEmail(author.getEmail())).thenReturn(Optional.of(MakeAuthor.AUTHOR.clone()));
    when(this.passwordEncoder.matches(author.getPassword(), author.getPassword())).thenReturn(true);
    when(this.jwtProvider.sign(author.getId().toString())).thenReturn(signResponse);

    AuthenticateAuthorResponseDTO token = this.authenticateAuthor.execute(authenticateAuthor);

    assertThat(token.token()).isInstanceOf(String.class);
    assertThat(token.expiresIn()).isInstanceOf(Long.class);
  }

  @Test
  @DisplayName("Should not be able to authenticate a author with invalid email")
  public void invalidEmail() {
    String wrongEmail = "wrong-emailg@example.com";
    AuthenticateAuthorDTO authenticateAuthor = new AuthenticateAuthorDTO(
        wrongEmail,
        "password");

    when(this.authorsRepository.findByEmail(wrongEmail)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.authenticateAuthor.execute(authenticateAuthor))
        .isInstanceOf(InvalidCredentialsException.class);
  }

  @Test
  @DisplayName("Should not be able to authenticate a author with invalid password")
  public void invalidPassword() {
    Author author = MakeAuthor.AUTHOR.clone();

    AuthenticateAuthorDTO authenticateAuthor = new AuthenticateAuthorDTO(
        author.getEmail(),
        "wrong-password");

    when(this.authorsRepository.findByEmail(author.getEmail())).thenReturn(Optional.of(MakeAuthor.AUTHOR.clone()));
    when(this.passwordEncoder.matches("wrong-password", author.getPassword())).thenReturn(false);

    assertThatThrownBy(() -> this.authenticateAuthor.execute(authenticateAuthor))
        .isInstanceOf(InvalidCredentialsException.class);
  }
}
