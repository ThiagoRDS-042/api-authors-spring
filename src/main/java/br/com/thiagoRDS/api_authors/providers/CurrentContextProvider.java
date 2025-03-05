package br.com.thiagoRDS.api_authors.providers;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CurrentContextProvider {
  public UriComponentsBuilder getUri() {
    return ServletUriComponentsBuilder.fromCurrentContextPath();
  }
}
