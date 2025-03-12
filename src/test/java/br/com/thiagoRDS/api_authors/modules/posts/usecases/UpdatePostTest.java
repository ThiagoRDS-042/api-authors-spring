package br.com.thiagoRDS.api_authors.modules.posts.usecases;

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

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.UpdatePostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.AuthorIsNotTheOwnerException;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostAlreadyRegisteredException;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostNotFoundException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@ExtendWith(MockitoExtension.class)
public class UpdatePostTest {
  @InjectMocks
  private UpdatePost updatePost;

  @Mock
  private PostsRepository postsRepository;

  @Test
  @DisplayName("Should be able to update a post")
  public void updatePost() {
    Post post = MakePost.POST.clone();

    UpdatePostDTO updatePost = new UpdatePostDTO(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getDescription(),
        post.getKeywords().split(";"),
        post.getAuthorId());

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.of(post));
    when(this.postsRepository.findByTitle(post.getTitle())).thenReturn(Optional.empty());

    assertThatCode(() -> this.updatePost.execute(updatePost)).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Should not be able to update a non-existing post")
  public void postNotFound() {
    Post post = MakePost.POST.clone();

    UpdatePostDTO updatePost = new UpdatePostDTO(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getDescription(),
        post.getKeywords().split(";"),
        post.getAuthorId());

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.updatePost.execute(updatePost)).isInstanceOf(PostNotFoundException.class);
  }

  @Test
  @DisplayName("Should not be able to update a post with not allowed author")
  public void authorNotAllowed() {
    UUID authorId = UUID.randomUUID();
    Post post = MakePost.POST.clone();

    UpdatePostDTO updatePost = new UpdatePostDTO(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getDescription(),
        post.getKeywords().split(";"),
        authorId);

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.of(post));

    assertThatThrownBy(() -> this.updatePost.execute(updatePost)).isInstanceOf(AuthorIsNotTheOwnerException.class);
  }

  @Test
  @DisplayName("Should not be able to update a post with same title another post")
  public void postAlreadyRegistered() {
    Post post = MakePost.POST.clone();
    Post anotherPost = new Post(
        UUID.randomUUID(),
        "another title",
        "content",
        "description",
        "keywords;keywords",
        0,
        1,
        LocalDateTime.now(),
        null,
        LocalDateTime.now(),
        UUID.randomUUID(),
        new Author());

    UpdatePostDTO updatePost = new UpdatePostDTO(
        post.getId(),
        anotherPost.getTitle(),
        post.getContent(),
        post.getDescription(),
        post.getKeywords().split(";"),
        post.getAuthorId());

    when(this.postsRepository.findById(post.getId())).thenReturn(Optional.of(post));
    when(this.postsRepository.findByTitle(anotherPost.getTitle())).thenReturn(Optional.of(anotherPost));

    assertThatThrownBy(() -> this.updatePost.execute(updatePost)).isInstanceOf(PostAlreadyRegisteredException.class);
  }
}
