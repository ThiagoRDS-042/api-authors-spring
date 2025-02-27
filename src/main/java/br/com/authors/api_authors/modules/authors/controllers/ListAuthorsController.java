package br.com.authors.api_authors.modules.authors.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.authors.api_authors.modules.authors.dtos.ListAuthorsDTO;
import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.mappers.AuthorMapper;
import br.com.authors.api_authors.modules.authors.usecases.ListAuthors;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/authors")
public class ListAuthorsController {
  @Autowired
  private ListAuthors listAuthors;

  @GetMapping("")
  public ResponseEntity<List<AuthorReponseMapperDTO>> list(@RequestParam(required = false) String email,
      @RequestParam(required = false) String tag, @RequestParam Integer page,
      @RequestParam Integer pageSize) {

    ListAuthorsDTO filters = new ListAuthorsDTO(email, tag, page, pageSize);

    List<Author> authors = this.listAuthors.execute(filters);

    List<AuthorReponseMapperDTO> authorsMapper = authors.stream().map(AuthorMapper::ToHttp).toList();

    return ResponseEntity.ok(authorsMapper);
  }
}
