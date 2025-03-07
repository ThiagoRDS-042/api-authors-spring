package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@ExtendWith(MockitoExtension.class)
public class DeleteAllPostsByAuthorIdTest {
  @InjectMocks
  private DeleteAllPostsByAuthorId deleteAllPostsByAuthorId;

  @Mock
  private PostsRepository postsRepository;

  @Test
  @DisplayName("Should be able to delete all posts by author id")
  public void deleteAllPosts() {
    assertThatCode(() -> this.deleteAllPostsByAuthorId.execute(MakePost.POST.getAuthorId())).doesNotThrowAnyException();
  }
}
