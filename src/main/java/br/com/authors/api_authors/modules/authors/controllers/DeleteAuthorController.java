package br.com.authors.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.usecases.DeleteAuthor;
import br.com.authors.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/authors")
public class DeleteAuthorController {
  @Autowired
  private DeleteAuthor deleteAuthor;

  @DeleteMapping("")
  public ResponseEntity<Object> postMethodName(HttpServletRequest request) {
    Object authorId = request.getAttribute(SessionId.ID);

    this.deleteAuthor.execute(UUID.fromString(authorId.toString()));

    return ResponseEntity.noContent().build();
  }
}
