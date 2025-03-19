package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.providers.JwtProvider.JwtProvider;
import br.com.thiagoRDS.api_authors.providers.JwtProvider.dtos.SignResponseDTO;
import br.com.thiagoRDS.api_authors.providers.StorageProvider.StorageProvider;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UploadAvatarControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private JwtProvider jwtProvider;

  @MockitoBean
  private StorageProvider storageProvider;

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
  @DisplayName("Should be able to upload a author avatar")
  public void uploadAvatar() throws Exception {
    Author author = MakeAuthor.AUTHOR.clone();
    author.setId(null);
    author.setAvatar("avatar.png");
    author = this.authorsRepository.saveAndFlush(author);

    SignResponseDTO response = this.jwtProvider.sign(author.getId().toString());

    byte[] inputArray = author.getAvatar().getBytes();

    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", author.getAvatar(), MediaType.IMAGE_PNG_VALUE,
        inputArray);

    this.mvc.perform(multipart(HttpMethod.PATCH, "/authors/avatar")
        .file(mockMultipartFile)
        .header("Authorization", "Bearer " + response.token()))
        .andExpect(status().isNoContent());
  }
}
