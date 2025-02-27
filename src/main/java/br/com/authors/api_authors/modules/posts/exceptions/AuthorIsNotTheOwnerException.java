package br.com.authors.api_authors.modules.posts.exceptions;

import org.springframework.http.HttpStatus;

import br.com.authors.api_authors.exceptions.AppException;

public class AuthorIsNotTheOwnerException extends AppException {

  public AuthorIsNotTheOwnerException() {
    super("Author is not the owner.", HttpStatus.FORBIDDEN);
  }

}
