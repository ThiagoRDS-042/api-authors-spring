package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.ListAuthorsDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.providers.GerericWhereSpecification;

@Service
public class ListAuthors {
  private final AuthorsRepository authorsRepository;

  public ListAuthors(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  public List<Author> execute(ListAuthorsDTO filters) {
    Pageable pageable = PageRequest.of(filters.page(), filters.pageSize(), Direction.DESC, "createdAt");

    Specification<Author> emailLike = new GerericWhereSpecification<Author>().like(filters.email(), "email");

    Specification<Author> tagLike = new GerericWhereSpecification<Author>().like(filters.tag(), "tag");

    Page<Author> authors = this.authorsRepository.findAll(emailLike.and(tagLike), pageable);

    return authors.getContent();
  }
}
