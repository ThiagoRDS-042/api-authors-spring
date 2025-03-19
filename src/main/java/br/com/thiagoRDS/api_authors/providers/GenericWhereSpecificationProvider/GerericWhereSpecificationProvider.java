package br.com.thiagoRDS.api_authors.providers.GenericWhereSpecificationProvider;

import org.springframework.data.jpa.domain.Specification;

public abstract class GerericWhereSpecificationProvider<G> {
  public abstract Specification<G> like(String filter, String fieldPath);

  public abstract Specification<G> notNull(String fieldPath);
}
