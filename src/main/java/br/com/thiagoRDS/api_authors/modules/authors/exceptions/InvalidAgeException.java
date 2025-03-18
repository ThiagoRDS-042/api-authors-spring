package br.com.thiagoRDS.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.thiagoRDS.api_authors.exceptions.AppException;

public class InvalidAgeException extends AppException {
  public InvalidAgeException() {
    super("Minimum age not reached.", HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
