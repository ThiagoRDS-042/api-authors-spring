package br.com.thiagoRDS.api_authors.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;

public record GenerateTag() {
  private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
  private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

  public static String generate(String username) {
    String nowhitespace = WHITESPACE.matcher(username).replaceAll("-");

    String normalized = Normalizer.normalize(nowhitespace, Form.NFD);

    String tag = NONLATIN.matcher(normalized).replaceAll("");

    return tag + "-" + System.currentTimeMillis();
  }
}
