package br.com.authors.api_authors.modules.authors.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.authors.api_authors.modules.authors.entities.RecoveryToken;

@Repository
public interface RecoveryTokensRepository extends JpaRepository<RecoveryToken, UUID> {
  @EntityGraph("RecoveryToken")
  Optional<RecoveryToken> findByAuthorId(UUID authorId);

  @EntityGraph("RecoveryToken.Author")
  Optional<RecoveryToken> findByCode(UUID code);
}
