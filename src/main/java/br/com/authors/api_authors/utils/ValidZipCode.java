package br.com.authors.api_authors.utils;

public record ValidZipCode() {
  public static final String FORMAT = "^\\d{5}-\\d{3}$";
  public static final String MESSAGE = "invalid zip code format";
}
