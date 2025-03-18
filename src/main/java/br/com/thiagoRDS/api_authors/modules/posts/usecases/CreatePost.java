package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.CreatePostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostAlreadyRegisteredException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;

@Service
public class CreatePost {

  private final PostsRepository postsRepository;
  private final AuthorsRepository authorsRepository;

  public CreatePost(PostsRepository postsRepository, AuthorsRepository authorsRepository) {
    this.postsRepository = postsRepository;
    this.authorsRepository = authorsRepository;
  }

  public Post execute(CreatePostDTO data) {
    this.postsRepository.findByTitle(data.title()).ifPresent((post) -> {
      throw new PostAlreadyRegisteredException();
    });

    Author author = this.authorsRepository.findById(data.authorId()).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    String keywords = String.join(";", data.keywords());

    Post post = new Post(data.title(), data.content(), data.description(), keywords, data.authorId(), author);

    post = this.postsRepository.save(post);

    return post;
  }
}
