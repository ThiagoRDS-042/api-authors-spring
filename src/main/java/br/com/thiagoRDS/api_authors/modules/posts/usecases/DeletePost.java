package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.DeletePostByIdDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.AuthorIsNotTheOwnerException;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;

@Service
public class DeletePost {
  private final PostsRepository postsRepository;

  public DeletePost(PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
  }

  public void execute(DeletePostByIdDTO data) {
    Post post = this.postsRepository.findById(data.postId()).orElseThrow(() -> {
      throw new PostNotFoundException();
    });

    if (!post.getAuthorId().equals(data.authorId())) {
      throw new AuthorIsNotTheOwnerException();
    }

    this.postsRepository.delete(post);
  }
}
