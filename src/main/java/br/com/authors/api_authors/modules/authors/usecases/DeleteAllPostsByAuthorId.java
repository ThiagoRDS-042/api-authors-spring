package br.com.authors.api_authors.modules.authors.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.posts.repositories.PostsRepository;

@Service
public class DeleteAllPostsByAuthorId {
  private final PostsRepository postsRepository;

  public DeleteAllPostsByAuthorId(PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
  }

  public void execute(UUID authorId) {
    this.postsRepository.deleteAllByAuthorId(authorId);
  }
}
