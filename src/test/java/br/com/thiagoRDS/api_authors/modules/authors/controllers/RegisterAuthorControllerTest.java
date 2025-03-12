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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.RegisterAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.utils.Convert;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RegisterAuthorControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @BeforeAll
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("Should be able to register a new author")
  public void registerAuthor() throws Exception {
    RegisterAuthorDTO registerAuthorDTO = MakeAuthor.REGISTER_AUTHOR_DTO;

    this.mvc.perform(post("/authors")
        .contentType(MediaType.APPLICATION_JSON)
        .content(Convert.objectToJSON(registerAuthorDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isString())
        .andExpect(jsonPath("$.tag").isString())
        .andExpect(jsonPath("$.address").doesNotExist())
        .andExpect(jsonPath("$.avatarUrl").doesNotExist())
        .andExpect(jsonPath("$.password").doesNotExist())
        .andExpect(jsonPath("$.name").value(registerAuthorDTO.name()))
        .andExpect(jsonPath("$.email").value(registerAuthorDTO.email()))
        .andExpect(jsonPath("$.birthdate").value(registerAuthorDTO.birthdate()));
  }
}
