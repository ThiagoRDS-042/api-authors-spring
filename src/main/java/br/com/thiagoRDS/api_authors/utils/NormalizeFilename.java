package br.com.thiagoRDS.api_authors.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;

public record NormalizeFilename() {
  private static final Pattern NONLATIN = Pattern.compile("[^\\w-\\.]");
  private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

  public static String normalize(String filename) {
    String nowhitespace = WHITESPACE.matcher(filename).replaceAll("-");

    String normalized = Normalizer.normalize(nowhitespace, Form.NFD);

    String normalizedFilename = NONLATIN.matcher(normalized).replaceAll("");

    return System.currentTimeMillis() + "-" + normalizedFilename;
  }
}
