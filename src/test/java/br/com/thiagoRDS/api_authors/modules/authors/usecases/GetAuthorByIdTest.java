package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorWithPostsDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.PostWithouAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAddress;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;
import br.com.thiagoRDS.api_authors.providers.CurrentContextProvider;

@ExtendWith(MockitoExtension.class)
public class GetAuthorByIdTest {
  @InjectMocks
  private GetAuthorById getAuthorById;

  @Mock
  private PostsRepository postsRepository;

  @Mock
  private AuthorsRepository authorsRepository;

  @Mock
  private CurrentContextProvider currentContextProvider;

  @Test
  @DisplayName("Should be able to get a author by id")
  public void getAuthorById() {
    Author author = MakeAuthor.AUTHOR.clone();
    Address address = MakeAddress.ADDRESS.clone();
    List<PostWithouAuthorDTO> posts = MakePost.POSTS_WITHOUT_AUTHOR;

    author.setAddress(address);

    UriComponentsBuilder currentContext = UriComponentsBuilder.fromPath("http://localhost:8080");

    AuthorWithPostsDTO authorWithPosts = new AuthorWithPostsDTO(
        author.getId(),
        author.getName(),
        author.getEmail(),
        author.getTag(),
        author.getAvatar(),
        author.getBirthdate(),
        author.getAddress(),
        posts);

    when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));
    when(this.postsRepository.findByAuthorId(author.getId())).thenReturn(posts);
    when(this.currentContextProvider.getUri()).thenReturn(currentContext);

    AuthorWithPostsDTO authorWithPostsResponse = this.getAuthorById.execute(author.getId());

    assertThat(authorWithPostsResponse).isEqualTo(authorWithPosts);
  }

  @Test
  @DisplayName("Should not be able to get a non-existing author")
  public void authorNotFound() {
    UUID authorId = UUID.randomUUID();

    when(this.authorsRepository.findById(authorId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.getAuthorById.execute(authorId))
        .isInstanceOf(AuthorNotFoundException.class);
  }
}
