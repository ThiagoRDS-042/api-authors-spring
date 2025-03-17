package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorMessageDTO;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorWithPostsDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.GetAuthorById;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Author", description = "Manage authors")
@RestController
@RequestMapping("/authors")
public class GetAuthorByIdController {
  @Autowired
  private GetAuthorById getAuthorById;

  @Operation(summary = "Get a author", description = "Get a author")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthorReponseMapperDTO.class))
      }, description = "Success"),
      @ApiResponse(responseCode = "400", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class)))
      }, description = "Validation failed."),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Author does not exists."),
  })
  @GetMapping("/{authorId}")
  public ResponseEntity<AuthorWithPostsDTO> getAuthor(@Valid @PathVariable UUID authorId) {
    AuthorWithPostsDTO author = this.getAuthorById.execute(authorId);

    return ResponseEntity.ok(author);
  }
}
