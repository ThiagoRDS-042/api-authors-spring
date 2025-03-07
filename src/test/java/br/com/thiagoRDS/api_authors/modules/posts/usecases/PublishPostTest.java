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

import br.com.thiagoRDS.api_authors.modules.posts.dtos.PublishPostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.AuthorIsNotTheOwnerException;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@ExtendWith(MockitoExtension.class)
public class PublishPostTest {
  @InjectMocks
  private PublishPost publishPost;

  @Mock
  private PostsRepository postsRepository;

  @Test
  @DisplayName("Should be able to publish a post")
  public void publishPost() {
    Post post = MakePost.POST;
    post.setPublishedAt(null);

    PublishPostDTO publishPost = new PublishPostDTO(post.getAuthorId(), post.getId());

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.of(post));

    assertThatCode(() -> this.publishPost.execute(publishPost)).doesNotThrowAnyException();
    assertThat(post.getPublishedAt()).isInstanceOf(LocalDateTime.class);
  }

  @Test
  @DisplayName("Should not be able to publish a non-existing post")
  public void postNotFound() {
    UUID postId = UUID.randomUUID();

    PublishPostDTO publishPost = new PublishPostDTO(UUID.randomUUID(), postId);

    when(this.postsRepository.findById(postId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.publishPost.execute(publishPost)).isInstanceOf(PostNotFoundException.class);
  }

  @Test
  @DisplayName("Should not be able to publish a post with not allowed author")
  public void authorNotAllowed() {
    UUID authorId = UUID.randomUUID();

    Post post = MakePost.POST;

    PublishPostDTO publishPost = new PublishPostDTO(authorId, post.getId());

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.of(post));

    assertThatThrownBy(() -> this.publishPost.execute(publishPost)).isInstanceOf(AuthorIsNotTheOwnerException.class);
  }
}
