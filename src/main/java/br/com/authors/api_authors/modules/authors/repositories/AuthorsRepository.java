package br.com.authors.api_authors.modules.authors.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.authors.api_authors.modules.authors.entities.Author;

public interface AuthorsRepository extends JpaRepository<Author, UUID> {
  Optional<Author> findByTagOrEmail(String tag, String email);
}
