package br.com.thiagoRDS.api_authors.config;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AddressesRepository;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;

@Configuration
public class CreateAuthorSeeder {
  @Value("${seeds.run}")
  private Boolean runSeed;

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private AuthorsRepository authorsRepository;

  @Autowired
  private AddressesRepository addressesRepository;

  private final Logger logger = LoggerFactory.getLogger(CreateAuthorSeeder.class);

  @Bean
  boolean run() {
    if (this.runSeed) {
      this.logger.info("Starting seeding 🚀");

      Address address = new Address(
          "address city",
          "address street",
          "12345-678",
          "ST",
          "address complement",
          "address neighorbood");

      Author author = new Author(
          "author name",
          "author-email@example.com",
          "author-tag",
          "Author-pass1",
          LocalDate.now());

      Post post = new Post(
          "post title",
          "post content",
          "post description",
          "post;keywords",
          author.getId());

      boolean authorPresent = this.authorsRepository.findByEmail(author.getEmail()).isPresent();

      try {
        if (!authorPresent) {
          address = this.addressesRepository.saveAndFlush(address);

          author.setAddressId(address.getId());
          author = this.authorsRepository.saveAndFlush(author);

          post.setAuthorId(author.getId());
          this.postsRepository.saveAndFlush(post);

        }

        this.logger.info("Seed finish with success ✔");
      } catch (Exception e) {
        this.logger.info("Seed finish with error ❌");
        this.logger.error(e.getMessage());

        if (address.getId() != null) {
          this.addressesRepository.delete(address);
        }

        if (author.getId() != null) {
          this.authorsRepository.delete(author);
        }
      }

      return this.runSeed;
    }

    return this.runSeed;
  }
}
