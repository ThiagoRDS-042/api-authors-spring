package br.com.authors.api_authors.modules.authors.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.authors.api_authors.modules.authors.entities.Author;

public interface AuthorsRepository extends JpaRepository<Author, UUID> {
  Optional<Author> findByEmail(String email);

  @Query("SELECT a FROM Author a JOIN FETCH a.address")
  List<Author> findMany();

  Optional<Author> findByTag(String tag);
}
