package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import br.com.thiagoRDS.api_authors.modules.posts.dtos.CreatePostControllerDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.utils.Convert;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;
import br.com.thiagoRDS.api_authors.providers.JwtProvider.JwtProvider;
import br.com.thiagoRDS.api_authors.providers.JwtProvider.dtos.SignResponseDTO;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CreatePostControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private JwtProvider jwtProvider;

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
  @DisplayName("Should be able to create a new post")
  public void createPost() throws Exception {
    Author author = MakeAuthor.AUTHOR.clone();
    author.setId(null);
    author = this.authorsRepository.saveAndFlush(author);

    Post post = MakePost.POST.clone();

    SignResponseDTO response = this.jwtProvider.sign(author.getId().toString());

    CreatePostControllerDTO createPostData = new CreatePostControllerDTO(
        post.getTitle(),
        post.getContent(),
        post.getDescription(),
        Stream.of(post.getKeywords().split(";")).collect(Collectors.toList()));

    this.mvc.perform(post("/posts")
        .header("Authorization", "Bearer " + response.token())
        .contentType(MediaType.APPLICATION_JSON)
        .content(Convert.objectToJSON(createPostData)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isString())
        .andExpect(jsonPath("$.title").value(post.getTitle()))
        .andExpect(jsonPath("$.content").value(post.getContent()))
        .andExpect(jsonPath("$.description").value(post.getDescription()))
        .andExpect(jsonPath("$.keywords").isArray())
        .andExpect(jsonPath("$.author.id").value(author.getId().toString()))
        .andExpect(jsonPath("$.author.tag").value(author.getTag()))
        .andExpect(jsonPath("$.author.name").value(author.getName()))
        .andExpect(jsonPath("$.author.email").value(author.getEmail()))
        .andExpect(jsonPath("$.author.address").doesNotExist())
        .andExpect(jsonPath("$.author.avatarUrl").doesNotExist())
        .andExpect(jsonPath("$.author.password").doesNotExist())
        .andExpect(jsonPath("$.author.birthdate").value(author.getBirthdate().toString()))
        .andExpect(jsonPath("$.up").value(post.getUp()))
        .andExpect(jsonPath("$.publishedAt").isString());
  }
}
