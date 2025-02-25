package br.com.authors.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.authors.api_authors.exceptions.AppException;

public class AuthorAlreadyRegisteredException extends AppException {
  public AuthorAlreadyRegisteredException() {
    super("Author already registered.", HttpStatus.CONFLICT);
  }

}
