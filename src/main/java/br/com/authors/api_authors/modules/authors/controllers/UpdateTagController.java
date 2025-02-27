package br.com.authors.api_authors.modules.authors.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.UpdateTagDTO;
import br.com.authors.api_authors.modules.authors.usecases.UpdateTag;
import br.com.authors.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/authors")
public class UpdateTagController {
  @Autowired
  private UpdateTag updateTag;

  @PatchMapping("/tag")
  public ResponseEntity<Object> updateTag(HttpServletRequest request,
      @Valid @RequestBody UpdateTagDTO data) {
    Object authorId = request.getAttribute(SessionId.ID);

    this.updateTag.execute(UUID.fromString(authorId.toString()), data.tag());

    return ResponseEntity.noContent().build();
  }
}
