package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.RegisterAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.InvalidAgeException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;

@ExtendWith(MockitoExtension.class)
public class RegisterAuthorTest {
  @InjectMocks
  private RegisterAuthor registerAuthor;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthorsRepository authorsRepository;

  @Test
  @DisplayName("Should be able to register a new author")
  public void registerAuthor() {
    RegisterAuthorDTO authorDTO = MakeAuthor.REGISTER_AUTHOR_DTO;

    when(this.authorsRepository.findByEmail(authorDTO.email())).thenReturn(Optional.empty());
    when(this.authorsRepository.findByTag(anyString())).thenReturn(Optional.empty());
    when(this.authorsRepository.save(any(Author.class))).thenReturn(MakeAuthor.AUTHOR);

    Author author = this.registerAuthor.execute(authorDTO);

    assertThat(author).isEqualTo(MakeAuthor.AUTHOR);
  }

  @Test
  @DisplayName("Should not be able to register a new author with same email another author")
  public void emailAlreadyExists() {
    RegisterAuthorDTO authorDTO = MakeAuthor.REGISTER_AUTHOR_DTO;

    when(this.authorsRepository.findByEmail(authorDTO.email()))
        .thenReturn(Optional.of(MakeAuthor.AUTHOR));

    assertThatThrownBy(() -> this.registerAuthor.execute(authorDTO))
        .isInstanceOf(AuthorAlreadyRegisteredException.class);
  }

  @Test
  @DisplayName("Should not be able to register a new author with a invalid age")
  public void invalidAge() {
    RegisterAuthorDTO authorDTO = MakeAuthor.AUTHOR_INVALID_AGE;

    when(this.authorsRepository.findByEmail(authorDTO.email())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.registerAuthor.execute(authorDTO))
        .isInstanceOf(InvalidAgeException.class);
  }
}
