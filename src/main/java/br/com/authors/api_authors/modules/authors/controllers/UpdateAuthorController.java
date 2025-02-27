package br.com.authors.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.authors.dtos.AddressDTO;
import br.com.authors.api_authors.modules.authors.dtos.UpdateAuthorControllerDTO;
import br.com.authors.api_authors.modules.authors.dtos.UpdateAuthorDTO;
import br.com.authors.api_authors.modules.authors.usecases.UpdateAuthor;
import br.com.authors.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
public class UpdateAuthorController {
  @Autowired
  private UpdateAuthor updateAuthor;

  @PutMapping("/account")
  public ResponseEntity<Object> update(HttpServletRequest request, @Valid @RequestBody UpdateAuthorControllerDTO data) {
    Object authorId = request.getAttribute(SessionId.ID);

    AddressDTO address = new AddressDTO(
        data.address().city(), data.address().street(), data.address().zipCode(), data.address().stateCode(),
        data.address().complement(), data.address().neighborhood());

    UpdateAuthorDTO updateAuthorData = new UpdateAuthorDTO(UUID.fromString(authorId.toString()), data.name(),
        data.email(), address,
        data.birthdate());

    this.updateAuthor.execute(updateAuthorData);

    return ResponseEntity.noContent().build();
  }
}
