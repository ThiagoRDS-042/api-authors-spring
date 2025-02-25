package br.com.authors.api_authors.modules.authors.exceptions;

public class AuthorAlreadyRegisteredException extends RuntimeException {

  public AuthorAlreadyRegisteredException() {
    super("Author already registered.");
  }

}
