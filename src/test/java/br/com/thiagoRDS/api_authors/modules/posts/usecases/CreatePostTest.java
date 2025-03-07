package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.CreatePostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.exceptions.PostAlreadyRegisteredException;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@ExtendWith(MockitoExtension.class)
public class CreatePostTest {
  @InjectMocks
  private CreatePost createPost;

  @Mock
  private PostsRepository postsRepository;

  @Mock
  private AuthorsRepository authorsRepository;

  @Test
  @DisplayName("Should be able to create a new post")
  public void createPost() {
    CreatePostDTO createPost = MakePost.CREATE_POST_DTO;

    when(this.postsRepository.findByTitle(createPost.title())).thenReturn(Optional.empty());
    when(this.authorsRepository.findById(createPost.authorId())).thenReturn(Optional.of(MakeAuthor.AUTHOR));
    when(this.postsRepository.save(any(Post.class))).thenReturn(MakePost.POST);

    Post post = this.createPost.execute(createPost);

    assertThat(post).isEqualTo(MakePost.POST);
  }

  @Test
  @DisplayName("Should not be able to create a new post with the same title another post")
  public void postAlreadyRegistered() {
    CreatePostDTO createPost = MakePost.CREATE_POST_DTO;

    when(this.postsRepository.findByTitle(createPost.title())).thenReturn(Optional.of(MakePost.POST));

    assertThatThrownBy(() -> this.createPost.execute(createPost)).isInstanceOf(PostAlreadyRegisteredException.class);
  }

  @Test
  @DisplayName("Should not be able to create a new post with non-existing author")
  public void authorNotFound() {
    CreatePostDTO createPost = MakePost.CREATE_POST_DTO;

    when(this.postsRepository.findByTitle(createPost.title())).thenReturn(Optional.empty());
    when(this.authorsRepository.findById(createPost.authorId())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.createPost.execute(createPost)).isInstanceOf(AuthorNotFoundException.class);
  }
}
