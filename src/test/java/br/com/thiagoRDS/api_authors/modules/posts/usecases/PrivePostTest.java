package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.PrivePostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.AuthorIsNotTheOwnerException;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@ExtendWith(MockitoExtension.class)
public class PrivePostTest {
  @InjectMocks
  private PrivePost privePost;

  @Mock
  private PostsRepository postsRepository;

  @Test
  @DisplayName("Should be able to prive a post")
  public void privePost() {
    Post post = MakePost.POST.clone();
    post.setPublishedAt(LocalDateTime.now());

    PrivePostDTO privePost = new PrivePostDTO(post.getAuthorId(), post.getId());

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.of(post));

    assertThatCode(() -> this.privePost.execute(privePost)).doesNotThrowAnyException();
    assertThat(post.getPublishedAt()).isNull();
  }

  @Test
  @DisplayName("Should not be able to prive a non-existing post")
  public void postNotFound() {
    UUID postId = UUID.randomUUID();

    PrivePostDTO privePost = new PrivePostDTO(UUID.randomUUID(), postId);

    when(this.postsRepository.findById(postId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.privePost.execute(privePost)).isInstanceOf(PostNotFoundException.class);
  }

  @Test
  @DisplayName("Should not be able to prive a post with not allowed author")
  public void authorNotAllowed() {
    UUID authorId = UUID.randomUUID();

    Post post = MakePost.POST.clone();

    PrivePostDTO privePost = new PrivePostDTO(authorId, post.getId());

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.of(post));

    assertThatThrownBy(() -> this.privePost.execute(privePost)).isInstanceOf(AuthorIsNotTheOwnerException.class);
  }
}
