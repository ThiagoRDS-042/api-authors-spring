package br.com.authors.api_authors.modules.posts.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.authors.api_authors.modules.posts.entities.Post;

@Repository
public interface PostsRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {
  Optional<Post> findByTitle(String title);
}
