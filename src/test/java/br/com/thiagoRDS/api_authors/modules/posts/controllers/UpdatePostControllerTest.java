package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.UpdatePostControllerDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.Convert;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;
import br.com.thiagoRDS.api_authors.providers.JwtProvider.JwtProvider;
import br.com.thiagoRDS.api_authors.providers.dtos.SignResponseDTO;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UpdatePostControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private AuthorsRepository authorsRepository;

  @BeforeAll
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("Should be able to publish a post")
  public void publishPost() throws Exception {
    Author author = MakeAuthor.AUTHOR.clone();
    author.setId(null);
    author = this.authorsRepository.saveAndFlush(author);

    Post post = MakePost.POST.clone();
    post.setId(null);
    post.setAuthorId(author.getId());
    post.setAuthor(author);
    post = this.postsRepository.saveAndFlush(post);

    SignResponseDTO response = this.jwtProvider.sign(author.getId().toString());

    UpdatePostControllerDTO updatePostData = new UpdatePostControllerDTO(
        post.getTitle(),
        post.getContent(),
        post.getDescription(),
        Stream.of(post.getKeywords().split(";")).collect(Collectors.toList()));

    this.mvc.perform(put("/posts/" + post.getId())
        .header("Authorization", "Bearer " + response.token())
        .contentType(MediaType.APPLICATION_JSON)
        .content(Convert.objectToJSON(updatePostData)))
        .andExpect(status().isNoContent());
  }
}
