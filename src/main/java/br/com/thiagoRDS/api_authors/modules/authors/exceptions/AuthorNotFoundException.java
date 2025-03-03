package br.com.thiagoRDS.api_authors.modules.authors.exceptions;

import org.springframework.http.HttpStatus;

import br.com.thiagoRDS.api_authors.exceptions.AppException;

public class AuthorNotFoundException extends AppException {
  public AuthorNotFoundException() {
    super("Author does not exist.", HttpStatus.NOT_FOUND);
  }
}
