package br.com.thiagoRDS.api_authors.modules.utils;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.entities.RecoveryToken;

public record MakeRecoveryToken() {
  public static final RecoveryToken RECOVERY_TOKEN = new RecoveryToken(
      UUID.randomUUID(),
      UUID.randomUUID(),
      UUID.randomUUID(),
      new Author(),
      LocalDateTime.now());
}
