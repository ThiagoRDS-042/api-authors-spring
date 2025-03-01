package br.com.authors.api_authors.utils;

import java.util.Arrays;
import java.util.List;

public record FileStorage() {
  public static final String STORAGE_PATH = "tmp";
  public static final Integer MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB in bytes
  public static final List<String> VALID_MIMETYPES = Arrays.asList("image/png", "image/jped", "image/jpg",
      "image/webp");
}
