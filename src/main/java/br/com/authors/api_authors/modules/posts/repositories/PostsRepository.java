package br.com.authors.api_authors.modules.posts.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.authors.api_authors.modules.authors.dtos.PostWithouAuthorDTO;
import br.com.authors.api_authors.modules.posts.entities.Post;

@Repository
public interface PostsRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {
  @EntityGraph("Post")
  Optional<Post> findByTitle(String title);

  @EntityGraph("Post")
  List<PostWithouAuthorDTO> findByAuthorId(UUID authorId);

  @EntityGraph("Post.author.address")
  Page<Post> findAll(Specification<Post> specification, Pageable pageable);

  @EntityGraph("Post.author.address")
  Optional<Post> findByIdAndPublishedAtNotNull(UUID authorId);
}
