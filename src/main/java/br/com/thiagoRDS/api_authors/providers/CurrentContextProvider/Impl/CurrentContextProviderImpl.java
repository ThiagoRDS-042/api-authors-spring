package br.com.thiagoRDS.api_authors.providers.CurrentContextProvider.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.thiagoRDS.api_authors.providers.CurrentContextProvider.CurrentContextProvider;

@Service
public class CurrentContextProviderImpl extends CurrentContextProvider {
  public UriComponentsBuilder getUri() {
    return ServletUriComponentsBuilder.fromCurrentContextPath();
  }
}
