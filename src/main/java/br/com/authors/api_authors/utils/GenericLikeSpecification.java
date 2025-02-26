package br.com.authors.api_authors.utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record GenericLikeSpecification<G>() {
  public Specification<G> like(String filter, String field) {
    var isEmpty = !StringUtils.hasLength(filter);

    Specification<G> likeSpecification = Specification.where(
        (root, query, builder) -> {
          var fieldUpperCase = builder.upper(root.get(field));

          return isEmpty ? null : builder.like(fieldUpperCase, "%" + filter.toUpperCase() + "%");
        });

    return likeSpecification;
  }
}
