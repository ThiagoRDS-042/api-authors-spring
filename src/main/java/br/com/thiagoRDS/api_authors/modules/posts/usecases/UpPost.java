package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;

@Service
public class UpPost {
  private final PostsRepository postsRepository;

  public UpPost(PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
  }

  public void execute(UUID postId) {
    Post post = this.postsRepository.findByIdAndPublishedAtNotNull(postId).orElseThrow(() -> {
      throw new PostNotFoundException();
    });

    this.postsRepository.upPost(post.getId(), post.getVersion());
  }
}
