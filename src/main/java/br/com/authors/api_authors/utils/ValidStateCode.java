package br.com.authors.api_authors.utils;

public record ValidStateCode() {
  public static final String FORMAT = "[A-Z]{2}";
  public static final String MESSAGE = "invalid state code format";
}
