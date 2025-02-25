package br.com.authors.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.usecases.GetAuthorById;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/authors")
public class GetAuthorByIdController {
  @Autowired
  private GetAuthorById getAuthorById;

  @GetMapping("/{authorId}")
  public Author getMethodName(@PathVariable() UUID authorId) {
    return this.getAuthorById.execute(authorId);
  }
}
