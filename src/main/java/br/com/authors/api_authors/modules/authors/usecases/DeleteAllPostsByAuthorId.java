package br.com.authors.api_authors.modules.authors.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.dtos.PostWithouAuthorDTO;

import br.com.authors.api_authors.modules.posts.repositories.PostsRepository;

@Service
public class DeleteAllPostsByAuthorId {
  private final PostsRepository postsRepository;

  public DeleteAllPostsByAuthorId(PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
  }

  public void execute(UUID authorId) {
    List<PostWithouAuthorDTO> posts = this.postsRepository.findByAuthorId(authorId);

    if (posts.size() > 0) {
      List<UUID> postsId = posts.stream().map((post) -> post.id()).toList();

      this.postsRepository.deleteAllById(postsId);
    }
  }
}
