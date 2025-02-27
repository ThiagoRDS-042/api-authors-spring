package br.com.authors.api_authors.modules.authors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.AuthenticateAuthorDTO;
import br.com.authors.api_authors.modules.authors.dtos.AuthenticateAuthorResponseDTO;
import br.com.authors.api_authors.modules.authors.usecases.AuthenticateAuthor;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
public class AuthenticateAuthorController {
  @Autowired
  private AuthenticateAuthor authenticateAuthor;

  @PostMapping("/auth")
  public ResponseEntity<AuthenticateAuthorResponseDTO> auth(@Valid @RequestBody AuthenticateAuthorDTO data) {
    AuthenticateAuthorResponseDTO response = this.authenticateAuthor.execute(data);

    return ResponseEntity.ok(response);
  }

}
