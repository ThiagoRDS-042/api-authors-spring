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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.authors.api_authors.modules.authors.dtos.PostWithouAuthorDTO;
import br.com.authors.api_authors.modules.posts.entities.Post;
import jakarta.transaction.Transactional;

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

  @Transactional
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("DELETE FROM Post p WHERE p.authorId = :authorId")
  void deleteAllByAuthorId(UUID authorId);

  @Transactional
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("UPDATE Post p SET up = p.up +1, version = p.version + 1 WHERE p.id = :postId AND p.version = :postVersion")
  void upPost(@Param("postId") UUID postId, @Param("postVersion") Integer postVersion);
}
