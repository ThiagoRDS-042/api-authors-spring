package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorWithPostsDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.GetAuthorById;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/authors")
public class GetAuthorByIdController {
  @Autowired
  private GetAuthorById getAuthorById;

  @GetMapping("/{authorId}")
  public ResponseEntity<AuthorWithPostsDTO> getAuthor(@Valid @PathVariable UUID authorId) {
    AuthorWithPostsDTO author = this.getAuthorById.execute(authorId);

    return ResponseEntity.ok(author);
  }
}
