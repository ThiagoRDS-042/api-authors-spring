package br.com.authors.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.authors.api_authors.exceptions.AppException;

public class AuthorNotFound extends AppException {
  public AuthorNotFound() {
    super("Author does not exist.", HttpStatus.NOT_FOUND);
  }
}
