package br.com.thiagoRDS.api_authors.modules.posts.exceptions;

import org.springframework.http.HttpStatus;

import br.com.thiagoRDS.api_authors.exceptions.AppException;

public class PostNotFoundException extends AppException {
  public PostNotFoundException() {
    super("Post does not exists.", HttpStatus.NOT_FOUND);
  }
}
