package br.com.authors.api_authors.modules.authors.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.dtos.AuthorWithPostsDTO;
import br.com.authors.api_authors.modules.authors.dtos.PostWithouAuthorDTO;
import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.authors.api_authors.modules.posts.repositories.PostsRepository;

@Service
public class GetAuthorById {
  private final PostsRepository postsRepository;
  private final AuthorsRepository authorsRepository;

  public GetAuthorById(PostsRepository postsRepository, AuthorsRepository authorsRepository) {
    this.postsRepository = postsRepository;
    this.authorsRepository = authorsRepository;
  }

  public AuthorWithPostsDTO execute(UUID authorId) {
    Author author = this.authorsRepository.findById(authorId).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    List<PostWithouAuthorDTO> posts = this.postsRepository.findByAuthorId(authorId);

    AuthorWithPostsDTO authorWithPosts = new AuthorWithPostsDTO(author.getId(), author.getName(),
        author.getEmail(), author.getTag(), author.getBirthdate(),
        author.getAddress(), posts);

    return authorWithPosts;
  }
}
