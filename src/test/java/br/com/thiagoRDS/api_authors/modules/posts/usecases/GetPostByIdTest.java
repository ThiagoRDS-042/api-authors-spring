package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import static org.assertj.core.api.Assertions.assertThat;
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

import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@ExtendWith(MockitoExtension.class)
public class GetPostByIdTest {
  @InjectMocks
  private GetPostById getPostById;

  @Mock
  private PostsRepository postsRepository;

  @Test
  @DisplayName("Should be able to get a post by id")
  public void getPost() {
    Post post = MakePost.POST;
    post.setPublishedAt(LocalDateTime.now());

    when(this.postsRepository.findByIdAndPublishedAtNotNull(post.getId())).thenReturn(Optional.of(post));

    Post response = this.getPostById.execute(post.getId());

    assertThat(response).isEqualTo(post);
  }

  @Test
  @DisplayName("Should not be able to get a non-existing post")
  public void postNotFound() {
    UUID postId = UUID.randomUUID();

    when(this.postsRepository.findByIdAndPublishedAtNotNull(postId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.getPostById.execute(postId)).isInstanceOf(PostNotFoundException.class);
  }
}
