package br.com.authors.api_authors.modules.authors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.exceptions.ErrorResponseDTO;
import br.com.authors.api_authors.modules.authors.dtos.RegisterAuthorDTO;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.authors.api_authors.modules.authors.exceptions.InvalidAgeException;
import br.com.authors.api_authors.modules.authors.usecases.RegisterAuthor;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
public class RegisterAuthorController {

  @Autowired
  private RegisterAuthor registerAuthor;

  @PostMapping()
  public ResponseEntity<Object> register(@Valid @RequestBody RegisterAuthorDTO data) {
    try {
      var author = this.registerAuthor.execute(data);

      return ResponseEntity.ok(author);
    } catch (AuthorAlreadyRegisteredException exception) {
      var error = new ErrorResponseDTO(exception.getMessage(), "AuthorAlreadyRegisteredException");

      return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    } catch (InvalidAgeException exception) {
      var error = new ErrorResponseDTO(exception.getMessage(), "InvalidAgeException");

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    } catch (Exception exception) {
      exception.printStackTrace();

      var error = new ErrorResponseDTO("Internal server error.", "InternalServerError");

      return ResponseEntity.internalServerError().body(error);
    }
  }

}
