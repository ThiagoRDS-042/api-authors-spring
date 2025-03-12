package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AddressesRepository;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAddress;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetAuthorByIdControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private AuthorsRepository authorsRepository;

  @Autowired
  private AddressesRepository addressesRepository;

  @BeforeAll
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("Should be able to get the author by id")
  public void getAuthor() throws Exception {
    Address address = MakeAddress.ADDRESS.clone();
    address.setId(null);
    address = this.addressesRepository.saveAndFlush(address);

    Author author = MakeAuthor.AUTHOR.clone();
    author.setId(null);
    author.setAddress(address);
    author.setAddressId(address.getId());
    author = this.authorsRepository.saveAndFlush(author);

    Post post = MakePost.POST.clone();
    post.setId(null);
    post.setAuthor(author);
    post.setAuthorId(author.getId());
    post = this.postsRepository.saveAndFlush(post);

    this.mvc.perform(get("/authors/" + author.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(author.getId().toString()))
        .andExpect(jsonPath("$.name").value(author.getName()))
        .andExpect(jsonPath("$.email").value(author.getEmail()))
        .andExpect(jsonPath("$.avatarUrl").doesNotExist())
        .andExpect(jsonPath("$.password").doesNotExist())
        .andExpect(jsonPath("$.birthdate").value(author.getBirthdate().toString()))
        .andExpect(jsonPath("$.address.id").value(address.getId().toString()))
        .andExpect(jsonPath("$.address.city").value(address.getCity()))
        .andExpect(jsonPath("$.address.street").value(address.getStreet()))
        .andExpect(jsonPath("$.address.zipCode").value(address.getZipCode()))
        .andExpect(jsonPath("$.address.stateCode").value(address.getStateCode()))
        .andExpect(jsonPath("$.address.complement").value(address.getComplement()))
        .andExpect(jsonPath("$.address.neighborhood").value(address.getNeighborhood()))
        .andExpect(jsonPath("$.posts").isArray())
        .andExpect(jsonPath("$.posts[0].id").value(post.getId().toString()))
        .andExpect(jsonPath("$.posts[0].title").value(post.getTitle()))
        .andExpect(jsonPath("$.posts[0].content").value(post.getContent()))
        .andExpect(jsonPath("$.posts[0].description").value(post.getDescription()))
        .andExpect(jsonPath("$.posts[0].keywords").value(post.getKeywords()))
        .andExpect(jsonPath("$.posts[0].up").value(post.getUp()))
        .andExpect(jsonPath("$.posts[0].publishedAt").value(post.getPublishedAt().toString()));
  }
}
