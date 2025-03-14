package br.com.thiagoRDS.api_authors.modules.utils;

import java.util.UUID;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;

public record MakeAddress() {
  public static final Address ADDRESS = new Address(
      UUID.randomUUID(),
      "city",
      "street",
      "12345-123",
      "TS",
      "complement",
      "neighborhood");
}
