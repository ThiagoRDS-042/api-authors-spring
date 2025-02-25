package br.com.authors.api_authors.modules.authors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.RegisterAuthorDTO;
import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.usecases.RegisterAuthor;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
public class RegisterAuthorController {

  @Autowired
  private RegisterAuthor registerAuthor;

  @PostMapping()
  public ResponseEntity<Author> register(@Valid @RequestBody RegisterAuthorDTO data) {
    var author = this.registerAuthor.execute(data);

    return ResponseEntity.ok(author);
  }
}
