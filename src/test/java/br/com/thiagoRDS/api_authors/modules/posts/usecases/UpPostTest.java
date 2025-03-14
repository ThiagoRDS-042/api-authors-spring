package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@ExtendWith(MockitoExtension.class)
public class UpPostTest {
  @InjectMocks
  private UpPost upPost;

  @Mock
  private PostsRepository postsRepository;

  @Test
  @DisplayName("Should be able to up a post")
  public void upPost() {
    Post post = MakePost.POST.clone();

    when(this.postsRepository.findByIdAndPublishedAtNotNull(post.getId())).thenReturn(Optional.of(post));

    assertThatCode(() -> this.upPost.execute(post.getId())).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Should not be able to up a non-existing post")
  public void postNotFound() {
    UUID postId = UUID.randomUUID();

    when(this.postsRepository.findByIdAndPublishedAtNotNull(postId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.upPost.execute(postId)).isInstanceOf(PostNotFoundException.class);
  }
}
