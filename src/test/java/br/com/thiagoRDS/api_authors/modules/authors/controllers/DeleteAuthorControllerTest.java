package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AddressesRepository;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAddress;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;
import br.com.thiagoRDS.api_authors.providers.JwtProvider;
import br.com.thiagoRDS.api_authors.providers.dtos.SignResponseDTO;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DeleteAuthorControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private AuthorsRepository authorsRepository;

  @Autowired
  private AddressesRepository addressesRepository;

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private JwtProvider jwtProvider;

  @BeforeAll
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("Should be able to a author and your dependencies")
  public void deleteAllPostsByAuthor() throws Exception {
    Address address = MakeAddress.ADDRESS.clone();
    address.setId(null);
    address = this.addressesRepository.saveAndFlush(address);

    Author author = MakeAuthor.AUTHOR.clone();
    author.setId(null);
    author.setAddressId(address.getId());
    author.setAddress(address);
    author = this.authorsRepository.saveAndFlush(author);

    Post post = MakePost.POST.clone();
    post.setId(null);
    post.setAuthorId(author.getId());
    post.setAuthor(author);
    this.postsRepository.saveAndFlush(post);

    SignResponseDTO response = this.jwtProvider.sign(author.getId().toString());

    this.mvc.perform(delete("/authors")
        .header("Authorization", response.token()))
        .andExpect(status().isNoContent());

    Optional<Address> addressResponse = this.addressesRepository.findById(address.getId());
    Optional<Author> authorResponse = this.authorsRepository.findById(author.getId());
    Optional<Post> postResponse = this.postsRepository.findById(post.getId());

    assertAll(
        () -> assertThat(addressResponse.isEmpty()).isTrue(),
        () -> assertThat(authorResponse.isEmpty()).isTrue(),
        () -> assertThat(postResponse.isEmpty()).isTrue());
  }
}
