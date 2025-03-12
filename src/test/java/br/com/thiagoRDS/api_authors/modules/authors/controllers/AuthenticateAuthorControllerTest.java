package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthenticateAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.Convert;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticateAuthorControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private AuthorsRepository authorsRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeAll
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("[E2E] Should be able to authenticate a author")
  public void autenticateAuthor() throws Exception {
    Author author = MakeAuthor.AUTHOR.clone();

    String password = "Password-123";

    author.setId(null);
    author.setPassword(this.passwordEncoder.encode(password));

    this.authorsRepository.saveAndFlush(author);

    AuthenticateAuthorDTO authenticateAuthor = new AuthenticateAuthorDTO(author.getEmail(), password);

    this.mvc.perform(post("/authors/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .content(Convert.objectToJSON(authenticateAuthor)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.token").isString())
        .andExpect(jsonPath("$.expiresIn").isNumber());
  }
}
