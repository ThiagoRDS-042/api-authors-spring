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

import br.com.thiagoRDS.api_authors.modules.posts.dtos.DeletePostByIdDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.AuthorIsNotTheOwnerException;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@ExtendWith(MockitoExtension.class)
public class DeletePostTest {
  @InjectMocks
  private DeletePost deletePost;

  @Mock
  private PostsRepository postsRepository;

  @Test
  @DisplayName("Should be able to delete a post")
  public void deletePost() {
    Post post = MakePost.POST;

    DeletePostByIdDTO deletePost = new DeletePostByIdDTO(post.getAuthorId(), post.getId());

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.of(post));

    assertThatCode(() -> this.deletePost.execute(deletePost)).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Should not be able to delete a non-existing post")
  public void postNotFound() {
    UUID postId = UUID.randomUUID();

    DeletePostByIdDTO deletePost = new DeletePostByIdDTO(UUID.randomUUID(), postId);

    when(this.postsRepository.findById(postId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.deletePost.execute(deletePost)).isInstanceOf(PostNotFoundException.class);
  }

  @Test
  @DisplayName("Should not be able to delete a post with author is not allowed")
  public void authorIsNotAllowed() {
    UUID authorId = UUID.randomUUID();

    Post post = MakePost.POST;

    DeletePostByIdDTO deletePost = new DeletePostByIdDTO(authorId, post.getId());

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.of(post));

    assertThatThrownBy(() -> this.deletePost.execute(deletePost)).isInstanceOf(AuthorIsNotTheOwnerException.class);
  }
}
