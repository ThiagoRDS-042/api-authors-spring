package br.com.thiagoRDS.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.thiagoRDS.api_authors.exceptions.AppException;

public class InvalidCredentialsException extends AppException {
  public InvalidCredentialsException() {
    super("Invalid credentials.", HttpStatus.UNAUTHORIZED);
  }
}
