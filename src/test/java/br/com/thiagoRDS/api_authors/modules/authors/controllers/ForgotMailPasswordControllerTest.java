package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.ForgotMailPasswordControllerDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.entities.RecoveryToken;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.RecoveryTokensRepository;
import br.com.thiagoRDS.api_authors.modules.utils.Convert;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ForgotMailPasswordControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private AuthorsRepository authorsRepository;

  @Autowired
  private RecoveryTokensRepository recoveryTokensRepository;

  @BeforeAll
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("Should be able to forgot mail password")
  public void forgotMailPassword() throws Exception {
    Author author = MakeAuthor.AUTHOR;
    author.setId(null);

    author = this.authorsRepository.saveAndFlush(author);

    ForgotMailPasswordControllerDTO data = new ForgotMailPasswordControllerDTO(author.getEmail());

    this.mvc.perform(
        post("/authors/forgot-password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Convert.objectToJSON(data)))
        .andExpect(status().isNoContent());

    Optional<RecoveryToken> token = this.recoveryTokensRepository.findByAuthorId(author.getId());

    assertThat(token.isPresent()).isTrue();
  }
}
