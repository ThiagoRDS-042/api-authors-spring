package br.com.thiagoRDS.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.thiagoRDS.api_authors.exceptions.AppException;

public class RecoveryTokenExpiredException extends AppException {
  public RecoveryTokenExpiredException() {
    super("Recovery token expired.", HttpStatus.FORBIDDEN);
  }
}
