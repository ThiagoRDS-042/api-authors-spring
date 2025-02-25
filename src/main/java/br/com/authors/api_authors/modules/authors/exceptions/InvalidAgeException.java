package br.com.authors.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.authors.api_authors.exceptions.AppException;

public class InvalidAgeException extends AppException {
  public InvalidAgeException() {
    super("Minimum age not reached.", HttpStatus.BAD_REQUEST);
  }
}
