package br.com.authors.api_authors.modules.authors.usecases;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.dtos.ListAuthorsDTO;
import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.authors.api_authors.utils.GenericLikeSpecification;

@Service
public class ListAuthors {
  private final AuthorsRepository authorsRepository;

  public ListAuthors(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  public List<Author> execute(ListAuthorsDTO filters) {
    var pageable = PageRequest.of(filters.page(), filters.pageSize(), Direction.DESC, "createdAt");

    var emailLike = new GenericLikeSpecification<Author>().like(filters.email(), "email");

    var tagLike = new GenericLikeSpecification<Author>().like(filters.tag(), "tag");

    var authors = this.authorsRepository.findAll(emailLike.and(tagLike), pageable);

    return authors.getContent();
  }
}
