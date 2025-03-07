package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.UpdatePasswordDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.InvalidCredentialsException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;

@ExtendWith(MockitoExtension.class)
public class UpdatePasswordTest {
  @InjectMocks
  private UpdatePassword updatePassword;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthorsRepository authorsRepository;

  @Test
  @DisplayName("Should be able to update the author password")
  public void updatePassword() {
    Author author = MakeAuthor.AUTHOR;

    String newPassword = "New-password123";

    UpdatePasswordDTO updatePassword = new UpdatePasswordDTO(
        author.getId(),
        author.getPassword(),
        newPassword,
        newPassword);

    when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));
    when(this.passwordEncoder.matches(author.getPassword(), author.getPassword())).thenReturn(true);
    when(this.passwordEncoder.encode(newPassword)).thenReturn("encoded" + newPassword);

    assertThatCode(() -> this.updatePassword.execute(updatePassword)).doesNotThrowAnyException();
    assertThat(author.getPassword()).isEqualTo("encoded" + newPassword);
    assertThat(author.getUpdtaedAt()).isInstanceOf(LocalDateTime.class);
  }

  @Test
  @DisplayName("Should not be able to update the author password with new password and confirm password does not matches")
  public void newPasswordDoesNotMatches() {
    UpdatePasswordDTO updatePassword = new UpdatePasswordDTO(
        UUID.randomUUID(),
        "Old-password123",
        "New-password123",
        "Confirm-password123");

    assertThatThrownBy(() -> this.updatePassword.execute(updatePassword))
        .isInstanceOf(InvalidCredentialsException.class);
  }

  @Test
  @DisplayName("Should not be able to update the author password with non-existing author")
  public void authorNotFound() {
    Author author = MakeAuthor.AUTHOR;

    String newPassword = "New-password123";

    UpdatePasswordDTO updatePassword = new UpdatePasswordDTO(
        author.getId(),
        author.getPassword(),
        newPassword,
        newPassword);

    when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.updatePassword.execute(updatePassword))
        .isInstanceOf(AuthorNotFoundException.class);
  }

  @Test
  @DisplayName("Should not be able to update the author password with new password and old password does not matches")
  public void passwordDoesNotMatches() {
    Author author = MakeAuthor.AUTHOR;

    String newPassword = "New-password123";

    UpdatePasswordDTO updatePassword = new UpdatePasswordDTO(
        author.getId(),
        author.getPassword(),
        newPassword,
        newPassword);

    when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));
    when(this.passwordEncoder.matches(author.getPassword(), author.getPassword())).thenReturn(false);

    assertThatThrownBy(() -> this.updatePassword.execute(updatePassword))
        .isInstanceOf(InvalidCredentialsException.class);
  }
}
