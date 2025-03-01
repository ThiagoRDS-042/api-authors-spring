package br.com.authors.api_authors.providers;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class GenerateTagProvider {
  private final Pattern NONLATIN = Pattern.compile("[^\\w-]");
  private final Pattern WHITESPACE = Pattern.compile("[\\s]");

  public String generateTag(String username) {
    String nowhitespace = WHITESPACE.matcher(username).replaceAll("-");

    String normalized = Normalizer.normalize(nowhitespace, Form.NFD);

    String tag = NONLATIN.matcher(normalized).replaceAll("");

    return tag + "-" + System.currentTimeMillis();
  }
}
