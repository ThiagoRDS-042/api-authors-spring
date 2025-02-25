package br.com.authors.api_authors.modules.authors.exceptions;

public class InvalidAgeException extends RuntimeException {
  public InvalidAgeException() {
    super("Minimum age not reached.");
  }

}
