package br.com.thiagoRDS.api_authors.modules.authors.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, UUID>, JpaSpecificationExecutor<Author> {
  @EntityGraph("Author")
  Optional<Author> findByEmail(String email);

  @EntityGraph("Author.address")
  Page<Author> findAll(Specification<Author> specification, Pageable pageable);

  @EntityGraph("Author")
  Optional<Author> findByTag(String tag);

  @EntityGraph("Author")
  Optional<Author> findByAvatar(String avatar);
}
