package br.com.thiagoRDS.api_authors.utils;

import java.util.List;

public record FileStorage() {
  public static final Integer MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB in bytes
  public static final List<String> VALID_MIMETYPES = List.of(
      "image/png",
      "image/jpeg",
      "image/jpg",
      "image/webp");
}
