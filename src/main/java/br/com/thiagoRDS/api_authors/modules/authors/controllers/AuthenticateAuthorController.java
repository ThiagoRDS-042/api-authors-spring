package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorMessageDTO;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthenticateAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthenticateAuthorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.AuthenticateAuthor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Author", description = "Manage authors")
@RestController
@RequestMapping("/authors")
public class AuthenticateAuthorController {
  @Autowired
  private AuthenticateAuthor authenticateAuthor;

  @Operation(summary = "Authenticate a author", description = "Authenticate a author")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthenticateAuthorResponseDTO.class))
      }, description = "Success"),
      @ApiResponse(responseCode = "400", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class)))
      }, description = "Validation failed."),
      @ApiResponse(responseCode = "401", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Invalid credentials."),
  })
  @PostMapping("/auth")
  public ResponseEntity<AuthenticateAuthorResponseDTO> auth(@Valid @RequestBody AuthenticateAuthorDTO data) {
    AuthenticateAuthorResponseDTO response = this.authenticateAuthor.execute(data);

    return ResponseEntity.ok(response);
  }

}
