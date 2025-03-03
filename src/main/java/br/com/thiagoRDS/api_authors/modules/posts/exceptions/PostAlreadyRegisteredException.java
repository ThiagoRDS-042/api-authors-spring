package br.com.thiagoRDS.api_authors.modules.posts.exceptions;

import org.springframework.http.HttpStatus;

import br.com.thiagoRDS.api_authors.exceptions.AppException;

public class PostAlreadyRegisteredException extends AppException {
  public PostAlreadyRegisteredException() {
    super("Post already registered.", HttpStatus.CONFLICT);
  }
}
