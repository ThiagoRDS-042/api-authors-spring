package br.com.authors.api_authors.modules.authors.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.mappers.AuthorMapper;
import br.com.authors.api_authors.modules.authors.mappers.dtos.AuthorReponseMapperDTO;
import br.com.authors.api_authors.modules.authors.usecases.ListAuthors;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/authors")
public class ListtAuthorsController {
  @Autowired
  private ListAuthors listAuthors;

  @GetMapping("")
  public ResponseEntity<List<AuthorReponseMapperDTO>> list() {
    var authors = this.listAuthors.execute();

    var authorsMapper = new ArrayList<AuthorReponseMapperDTO>();

    authors.forEach((author) -> authorsMapper.add(AuthorMapper.ToHttp(author)));

    return ResponseEntity.ok(authorsMapper);
  }
}
