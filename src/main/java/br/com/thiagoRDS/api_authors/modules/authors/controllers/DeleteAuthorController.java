package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.modules.authors.usecases.DeleteAllPostsByAuthorId;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.DeleteAuthor;
import br.com.thiagoRDS.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/authors")
public class DeleteAuthorController {
  @Autowired
  private DeleteAuthor deleteAuthor;

  @Autowired
  private DeleteAllPostsByAuthorId deleteAllPostsByAuthorId;

  @DeleteMapping("")
  public ResponseEntity<Object> postMethodName(HttpServletRequest request) {
    Object sessionId = request.getAttribute(SessionId.ID);

    UUID authorId = UUID.fromString(sessionId.toString());

    this.deleteAllPostsByAuthorId.execute(authorId);

    this.deleteAuthor.execute(authorId);

    return ResponseEntity.noContent().build();
  }
}
