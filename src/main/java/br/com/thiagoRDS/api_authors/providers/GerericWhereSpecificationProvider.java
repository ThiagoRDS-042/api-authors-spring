package br.com.thiagoRDS.api_authors.providers;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;

@Service
public class GerericWhereSpecificationProvider<G> {
  public Specification<G> like(String filter, String fieldPath) {
    List<String> paths = List.of(fieldPath.split("\\."));

    boolean isEmpty = !StringUtils.hasLength(filter);

    Specification<G> likeSpecification = Specification.where(
        (root, query, builder) -> {
          Path<String> path = root.get(paths.get(0));

          for (int i = 1; i < paths.size(); i++) {
            path = path.get(paths.get(i));
          }

          Expression<String> fieldUpperCase = builder.upper(path);

          return isEmpty ? null : builder.like(fieldUpperCase, "%" + filter.toUpperCase() + "%");
        });

    return likeSpecification;
  }

  public Specification<G> notNull(String fieldPath) {
    List<String> paths = List.of(fieldPath.split("\\."));

    Specification<G> likeSpecification = Specification.where(
        (root, query, builder) -> {
          Path<String> path = root.get(paths.get(0));

          for (int i = 1; i < paths.size(); i++) {
            path = path.get(paths.get(i));
          }

          return builder.isNull(path).not();
        });

    return likeSpecification;
  }
}
