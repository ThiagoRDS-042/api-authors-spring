package br.com.authors.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.authors.api_authors.exceptions.AppException;

public class InvalidCredentials extends AppException {
  public InvalidCredentials() {
    super("Invalid credentials.", HttpStatus.UNAUTHORIZED);
  }
}
