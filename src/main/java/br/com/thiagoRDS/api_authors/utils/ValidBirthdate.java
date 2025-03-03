package br.com.thiagoRDS.api_authors.utils;

public record ValidBirthdate() {
  public static final String FORMAT = "\\d{4}-\\d{2}-\\d{2}";
  public static final String MESSAGE = "Invalid date format";
}
