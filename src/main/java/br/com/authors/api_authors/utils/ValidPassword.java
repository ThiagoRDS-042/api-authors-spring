package br.com.authors.api_authors.utils;

public record ValidPassword() {
  public static final String FORMAT = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";
  public static final String MESSAGE = "Invalid password format";
}
