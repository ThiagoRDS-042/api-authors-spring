package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.thiagoRDS.api_authors.config.MinioConfig;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.providers.MinioProvider;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetAvatarControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private AuthorsRepository authorsRepository;

  @Autowired
  private MinioProvider minioProvider;

  @Autowired
  private MinioConfig minioconfig;

  @BeforeAll
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();

    this.minioProvider.createBucket(this.minioconfig.getBucketName());
  }

  @AfterAll
  public void teardown() {
    this.minioProvider.deleteBucket(this.minioconfig.getBucketName());
  }

  @Test
  @DisplayName("Should be able to get a author avatar")
  public void getAvatar() throws Exception {
    Author author = MakeAuthor.AUTHOR.clone();
    author.setAvatar("avatar.png");
    author.setId(null);
    this.authorsRepository.saveAndFlush(author);

    byte[] inputArray = author.getAvatar().getBytes();

    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", author.getAvatar(), "image/png", inputArray);

    String bucketName = this.minioconfig.getBucketName();

    this.minioProvider.uploadFile(bucketName, mockMultipartFile, author.getAvatar());

    this.mvc.perform(get("/authors/avatar/" + author.getAvatar()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));

    this.minioProvider.deleteFile(bucketName, author.getAvatar());
  }
}
