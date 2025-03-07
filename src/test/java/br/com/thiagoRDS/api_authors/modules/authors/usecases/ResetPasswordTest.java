package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.ResetPasswordDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.entities.RecoveryToken;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.RecoveryTokenExpiredException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.RecoveryTokenNotFountException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.RecoveryTokensRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakeRecoveryToken;

@ExtendWith(MockitoExtension.class)
public class ResetPasswordTest {
  @InjectMocks
  private ResetPassword resetPassword;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthorsRepository authorsRepository;

  @Mock
  private RecoveryTokensRepository recoveryTokensRepository;

  @Test
  @DisplayName("Should be able to reset password")
  public void resetPassword() {
    RecoveryToken recoveryToken = MakeRecoveryToken.RECOVERY_TOKEN;
    Author author = MakeAuthor.AUTHOR;

    String password = "New-password123";

    recoveryToken.setAuthor(author);
    recoveryToken.setExpiresAt(LocalDateTime.now().plus(Duration.ofMinutes(5)));

    ResetPasswordDTO resetPassword = new ResetPasswordDTO(recoveryToken.getCode(), password);

    when(this.recoveryTokensRepository.findByCode(recoveryToken.getCode())).thenReturn(Optional.of(recoveryToken));
    when(this.passwordEncoder.encode(password)).thenReturn("encoded" + password);

    assertThatCode(() -> this.resetPassword.execute(resetPassword)).doesNotThrowAnyException();
    assertThat(author.getPassword()).isEqualTo("encoded" + password);
  }

  @Test
  @DisplayName("Should not be able to reset password with non-existing token recovery")
  public void tokenRecoveryNotFound() {
    RecoveryToken recoveryToken = MakeRecoveryToken.RECOVERY_TOKEN;

    ResetPasswordDTO resetPassword = new ResetPasswordDTO(recoveryToken.getCode(), "New-password123");

    when(this.recoveryTokensRepository.findByCode(recoveryToken.getCode())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.resetPassword.execute(resetPassword))
        .isInstanceOf(RecoveryTokenNotFountException.class);
  }

  @Test
  @DisplayName("Should not be able to reset password with expired token recovery")
  public void tokenRecoveryExpired() {
    RecoveryToken recoveryToken = MakeRecoveryToken.RECOVERY_TOKEN;
    Author author = MakeAuthor.AUTHOR;

    String password = "New-password123";

    recoveryToken.setAuthor(author);
    recoveryToken.setExpiresAt(LocalDateTime.now().minus(Duration.ofMinutes(5)));

    ResetPasswordDTO resetPassword = new ResetPasswordDTO(recoveryToken.getCode(), password);

    when(this.recoveryTokensRepository.findByCode(recoveryToken.getCode())).thenReturn(Optional.of(recoveryToken));

    assertThatThrownBy(() -> this.resetPassword.execute(resetPassword))
        .isInstanceOf(RecoveryTokenExpiredException.class);
  }
}
