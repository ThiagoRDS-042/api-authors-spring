package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ListAuthorsControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

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
  @DisplayName("Should be able to list authors")
  public void listAuthors() throws Exception {
    String baseTag = "author-tag";
    String baseEmail = "author-email";

    Author author = MakeAuthor.AUTHOR.clone();
    author.setId(null);
    author.setTag(baseTag + "1");
    author.setEmail(baseEmail + "1@example.com");

    Author anotherAuthor = MakeAuthor.AUTHOR.clone();
    anotherAuthor.setId(null);
    anotherAuthor.setTag(baseTag + "2");
    anotherAuthor.setEmail(baseEmail + "2@example.com");

    List<Author> authors = List.of(author, anotherAuthor);

    this.authorsRepository.saveAllAndFlush(authors);

    this.mvc.perform(get("/authors")
        .queryParam("page", "0")
        .queryParam("pageSize", "10")
        .queryParam("tag", baseTag)
        .queryParam("email", baseEmail))
        .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
  }
}
