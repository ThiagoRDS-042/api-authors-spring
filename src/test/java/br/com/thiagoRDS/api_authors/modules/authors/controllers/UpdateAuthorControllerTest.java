package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AddressDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.UpdateAuthorControllerDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.Convert;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAddress;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.providers.JwtProvider;
import br.com.thiagoRDS.api_authors.providers.dtos.SignResponseDTO;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UpdateAuthorControllerTest {
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
  @DisplayName("Should be able to update a author account")
  public void updateAuthor() throws Exception {
    Author author = MakeAuthor.AUTHOR.clone();
    author.setId(null);
    author = this.authorsRepository.saveAndFlush(author);

    Address address = MakeAddress.ADDRESS.clone();

    AddressDTO addressData = new AddressDTO(
        address.getCity(),
        address.getStreet(),
        address.getZipCode(),
        address.getStateCode(),
        address.getComplement(),
        address.getNeighborhood());

    UpdateAuthorControllerDTO updateAuthorData = new UpdateAuthorControllerDTO(
        "new-name",
        "new-email@example.com",
        addressData,
        LocalDate.now().minusYears(20).toString());

    SignResponseDTO response = this.jwtProvider.sign(author.getId().toString());

    this.mvc.perform(put("/authors/account")
        .header("Authorization", "Bearer " + response.token())
        .contentType(MediaType.APPLICATION_JSON)
        .content(Convert.objectToJSON(updateAuthorData)))
        .andExpect(status().isNoContent());
  }
}
