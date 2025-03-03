package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.PrivePostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.AuthorIsNotTheOwnerException;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;

@Service
public class PrivePost {
  private final PostsRepository postsRepository;

  public PrivePost(PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
  }

  public void execute(PrivePostDTO data) {
    Post post = this.postsRepository.findById(data.postId()).orElseThrow(() -> {
      throw new PostNotFoundException();
    });

    if (!post.getAuthorId().equals(data.authorId())) {
      throw new AuthorIsNotTheOwnerException();
    }

    post.setPublishedAt(null);
    post.setUpdtaedAt(LocalDateTime.now());

    this.postsRepository.save(post);
  }
}
