package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.entities.RecoveryToken;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.RecoveryTokensRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakeRecoveryToken;
import br.com.thiagoRDS.api_authors.providers.CurrentContextProvider;
import br.com.thiagoRDS.api_authors.providers.MailProvider;

@ExtendWith(MockitoExtension.class)
public class ForgotMailPasswordTest {
  @InjectMocks
  private ForgotMailPassword forgotMailPassword;

  @Mock
  private MailProvider mailProvider;

  @Mock
  private AuthorsRepository authorsRepository;

  @Mock
  private CurrentContextProvider currentContextProvider;

  @Mock
  private RecoveryTokensRepository recoveryTokensRepository;

  @Test
  @DisplayName("Should be able to create a new recovery token")
  public void createNewRecoveryToken() {
    Author author = MakeAuthor.AUTHOR;

    UriComponentsBuilder currentContext = UriComponentsBuilder.fromPath("http://localhost:8080");

    when(this.authorsRepository.findByEmail(author.getEmail())).thenReturn(Optional.of(author));
    when(this.currentContextProvider.getUri()).thenReturn(currentContext);

    this.forgotMailPassword.execute(author.getEmail());
  }

  @Test
  @DisplayName("Should be able to update a recovery token")
  public void updateRecoveryToken() {
    Author author = MakeAuthor.AUTHOR;
    RecoveryToken recoveryToken = MakeRecoveryToken.RECOVERY_TOKEN;

    UriComponentsBuilder currentContext = UriComponentsBuilder.fromPath("http://localhost:8080");

    when(this.authorsRepository.findByEmail(author.getEmail())).thenReturn(Optional.of(author));
    when(this.recoveryTokensRepository.findByAuthorId(author.getId())).thenReturn(Optional.of(recoveryToken));
    when(this.currentContextProvider.getUri()).thenReturn(currentContext);

    this.forgotMailPassword.execute(author.getEmail());
  }

  @Test
  @DisplayName("Should not be able to create a new recovery token with non-existing author")
  public void authorNotFound() {
    assertThatThrownBy(() -> this.forgotMailPassword.execute("non-existing-author@example.com"))
        .isInstanceOf(AuthorNotFoundException.class);
  }
}
