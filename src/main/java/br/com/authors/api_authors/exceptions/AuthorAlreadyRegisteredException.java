package br.com.authors.api_authors.exceptions;

public class AuthorAlreadyRegisteredException extends RuntimeException {

  public AuthorAlreadyRegisteredException() {
    super("Author already registered.");
  }
}
