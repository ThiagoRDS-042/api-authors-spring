package br.com.authors.api_authors.modules.posts.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.posts.entities.Post;
import br.com.authors.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.authors.api_authors.modules.posts.repositories.PostsRepository;

@Service
public class GetPostById {
  private final PostsRepository postsRepository;

  public GetPostById(PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
  }

  public Post execute(UUID postId) {
    Post post = this.postsRepository.findByIdAndPublishedAtNotNull(postId).orElseThrow(() -> {
      throw new PostNotFoundException();
    });

    return post;
  }
}
