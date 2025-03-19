package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;
import br.com.thiagoRDS.api_authors.providers.JwtProvider.JwtProvider;
import br.com.thiagoRDS.api_authors.providers.dtos.SignResponseDTO;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DeletePostControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private AuthorsRepository authorsRepository;

  @Autowired
  private PostsRepository postsRepository;

  @BeforeAll
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("Should be able to delete a post")
  public void deletePost() throws Exception {
    Author author = MakeAuthor.AUTHOR.clone();
    author.setId(null);
    author = this.authorsRepository.saveAndFlush(author);

    Post post = MakePost.POST.clone();
    post.setId(null);
    post.setAuthor(author);
    post.setAuthorId(author.getId());
    post = this.postsRepository.saveAndFlush(post);

    SignResponseDTO response = this.jwtProvider.sign(author.getId().toString());

    this.mvc.perform(delete("/posts/" + post.getId())
        .header("Authorization", "Bearer " + response.token()))
        .andExpect(status().isNoContent());

    Optional<Post> postResponse = this.postsRepository.findById(post.getId());

    assertThat(postResponse).isEmpty();
  }
}
