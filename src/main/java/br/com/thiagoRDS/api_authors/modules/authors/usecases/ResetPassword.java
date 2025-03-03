package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.ResetPasswordDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.entities.RecoveryToken;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.RecoveryTokenExpiredException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.RecoveryTokenNotFountException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.RecoveryTokensRepository;

@Service
public class ResetPassword {
  private final PasswordEncoder passwordEncoder;
  private final AuthorsRepository authorsRepository;
  private final RecoveryTokensRepository recoveryTokensRepository;

  public ResetPassword(PasswordEncoder passwordEncoder, AuthorsRepository authorsRepository,
      RecoveryTokensRepository recoveryTokensRepository) {
    this.passwordEncoder = passwordEncoder;
    this.authorsRepository = authorsRepository;
    this.recoveryTokensRepository = recoveryTokensRepository;
  }

  public void execute(ResetPasswordDTO data) {
    RecoveryToken token = this.recoveryTokensRepository.findByCode(data.code()).orElseThrow(() -> {
      throw new RecoveryTokenNotFountException();
    });

    if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
      throw new RecoveryTokenExpiredException();
    }

    String passwordEncoded = this.passwordEncoder.encode(data.password());

    Author author = token.getAuthor();

    author.setPassword(passwordEncoded);

    this.authorsRepository.save(author);

    this.recoveryTokensRepository.delete(token);
  }
}
