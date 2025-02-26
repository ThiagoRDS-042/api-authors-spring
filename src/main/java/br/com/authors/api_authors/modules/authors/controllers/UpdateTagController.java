package br.com.authors.api_authors.modules.authors.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.UpdateTagDTO;
import br.com.authors.api_authors.modules.authors.usecases.UpdateTag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/authors")
public class UpdateTagController {
  @Autowired
  private UpdateTag updateTag;

  @PutMapping("/tag")
  public ResponseEntity<Object> updateTag(HttpServletRequest request,
      @Valid @RequestBody UpdateTagDTO data) {
    var authorId = request.getAttribute("authorId");

    this.updateTag.execute(UUID.fromString(authorId.toString()), data.tag());

    return ResponseEntity.noContent().build();
  }
}
