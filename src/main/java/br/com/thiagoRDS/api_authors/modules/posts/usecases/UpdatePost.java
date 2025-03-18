package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.UpdatePostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.AuthorIsNotTheOwnerException;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostAlreadyRegisteredException;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;

@Service
public class UpdatePost {
  private final PostsRepository postsRepository;

  public UpdatePost(PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
  }

  public void execute(UpdatePostDTO data) {
    Post post = this.postsRepository.findById(data.postId()).orElseThrow(() -> {
      throw new PostNotFoundException();
    });

    if (!post.getAuthorId().equals(data.authorId())) {
      throw new AuthorIsNotTheOwnerException();
    }

    Optional<Post> postAlreadyRegistered = this.postsRepository.findByTitle(data.title());

    if (postAlreadyRegistered.isPresent() && postAlreadyRegistered.get().getId() != post.getId()) {
      throw new PostAlreadyRegisteredException();
    }

    String keywords = String.join(";", data.keywords());

    post.setDescription(data.description());
    post.setContent(data.content());
    post.setTitle(data.title());
    post.setKeywords(keywords);
    post.setUpdtaedAt(LocalDateTime.now());

    this.postsRepository.save(post);
  }
}
