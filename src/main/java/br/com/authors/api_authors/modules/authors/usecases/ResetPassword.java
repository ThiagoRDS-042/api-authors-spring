package br.com.authors.api_authors.modules.authors.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.dtos.ResetPasswordDTO;
import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class ResetPassword {
  private final PasswordEncoder passwordEncoder;
  private final AuthorsRepository authorsRepository;

  public ResetPassword(PasswordEncoder passwordEncoder, AuthorsRepository authorsRepository) {
    this.passwordEncoder = passwordEncoder;
    this.authorsRepository = authorsRepository;
  }

  public void execute(ResetPasswordDTO data) {
    Author author = this.authorsRepository.findById(data.code()).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    // TODO: buscar code pelo cache, redis.

    String passwordEncoded = this.passwordEncoder.encode(data.password());

    author.setPassword(passwordEncoded);

    this.authorsRepository.save(author);
  }
}
