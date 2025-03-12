package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorWithPostsDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.PostWithouAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.providers.CurrentContextProvider;

@Service
public class GetAuthorById {
  private final PostsRepository postsRepository;
  private final AuthorsRepository authorsRepository;
  private final CurrentContextProvider currentContextProvider;

  public GetAuthorById(PostsRepository postsRepository, AuthorsRepository authorsRepository,
      CurrentContextProvider currentContextProvider) {
    this.postsRepository = postsRepository;
    this.authorsRepository = authorsRepository;
    this.currentContextProvider = currentContextProvider;
  }

  public AuthorWithPostsDTO execute(UUID authorId) {
    Author author = this.authorsRepository.findById(authorId).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    List<PostWithouAuthorDTO> posts = this.postsRepository.findByAuthorId(authorId);

    UriComponentsBuilder currentUri = this.currentContextProvider.getUri();

    String avatarUrl = null;

    if (author.getAvatar() != null) {
      avatarUrl = currentUri.path("/authors/avatar/").path(author.getAvatar()).toUriString();
    }

    AuthorWithPostsDTO authorWithPosts = new AuthorWithPostsDTO(
        author.getId(),
        author.getName(),
        author.getEmail(),
        author.getTag(),
        avatarUrl,
        author.getBirthdate(),
        author.getAddress(),
        posts);

    return authorWithPosts;
  }
}
