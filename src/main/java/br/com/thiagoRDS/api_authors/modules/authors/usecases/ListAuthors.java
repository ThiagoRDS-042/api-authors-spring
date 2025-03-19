package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.ListAuthorsDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.providers.GenericWhereSpecificationProvider.GerericWhereSpecificationProvider;

@Service
public class ListAuthors {
  private final AuthorsRepository authorsRepository;
  private final GerericWhereSpecificationProvider<Author> gerericWhereSpecificationProvider;

  public ListAuthors(AuthorsRepository authorsRepository,
      GerericWhereSpecificationProvider<Author> gerericWhereSpecificationProvider) {
    this.authorsRepository = authorsRepository;
    this.gerericWhereSpecificationProvider = gerericWhereSpecificationProvider;
  }

  public List<Author> execute(ListAuthorsDTO filters) {
    Sort sortBy = Sort.by("createdAt").descending();
    Pageable pageable = PageRequest.of(filters.page(), filters.pageSize(), sortBy);

    Specification<Author> emailLike = this.gerericWhereSpecificationProvider.like(filters.email(), "email");

    Specification<Author> tagLike = this.gerericWhereSpecificationProvider.like(filters.tag(), "tag");

    Page<Author> authors = this.authorsRepository.findAll(emailLike.and(tagLike), pageable);

    return authors.getContent();
  }
}
