package br.com.thiagoRDS.api_authors.providers.CurrentContextProvider;

import org.springframework.web.util.UriComponentsBuilder;

public abstract class CurrentContextProvider {
  public abstract UriComponentsBuilder getUri();
}
