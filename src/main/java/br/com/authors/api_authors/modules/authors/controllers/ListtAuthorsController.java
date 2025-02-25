package br.com.authors.api_authors.modules.authors.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.usecases.ListAuthors;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/authors")
public class ListtAuthorsController {
  @Autowired
  private ListAuthors listAuthors;

  @GetMapping("")
  public List<Author> list() {
    return this.listAuthors.execute();
  }
}
