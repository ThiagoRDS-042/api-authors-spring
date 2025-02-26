package br.com.authors.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.authors.api_authors.modules.authors.mappers.AuthorMapper;
import br.com.authors.api_authors.modules.authors.usecases.GetAuthorById;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/authors")
public class GetAuthorByIdController {
  @Autowired
  private GetAuthorById getAuthorById;

  @GetMapping("/{authorId}")
  public ResponseEntity<AuthorReponseMapperDTO> getAuthor(@PathVariable() UUID authorId) {
    var author = this.getAuthorById.execute(authorId);

    var authorMapper = AuthorMapper.ToHttp(author);

    return ResponseEntity.ok(authorMapper);
  }
}
