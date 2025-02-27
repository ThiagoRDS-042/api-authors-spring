package br.com.authors.api_authors.modules.authors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.authors.api_authors.modules.authors.dtos.RegisterAuthorDTO;
import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.mappers.AuthorMapper;
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
  public ResponseEntity<AuthorReponseMapperDTO> register(@Valid @RequestBody RegisterAuthorDTO data) {
    Author author = this.registerAuthor.execute(data);

    AuthorReponseMapperDTO authorMapper = AuthorMapper.ToHttp(author);

    return ResponseEntity.ok(authorMapper);
  }
}
