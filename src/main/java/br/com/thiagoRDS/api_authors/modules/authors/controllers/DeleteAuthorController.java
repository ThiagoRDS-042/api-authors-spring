package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.config.SwaggerConfig;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.DeleteAllPostsByAuthorId;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.DeleteAuthor;
import br.com.thiagoRDS.api_authors.utils.SessionId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;

@Tag(name = "Author", description = "Manage authors")
@RestController
@RequestMapping("/authors")
public class DeleteAuthorController {
  @Autowired
  private DeleteAuthor deleteAuthor;

  @Autowired
  private DeleteAllPostsByAuthorId deleteAllPostsByAuthorId;

  @SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
  @Operation(summary = "Delete a author", description = "Delete a author")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Success"),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Author does not exists.")
  })
  @DeleteMapping("")
  public ResponseEntity<Object> postMethodName(HttpServletRequest request) {
    Object sessionId = request.getAttribute(SessionId.ID);

    UUID authorId = UUID.fromString(sessionId.toString());

    this.deleteAllPostsByAuthorId.execute(authorId);

    this.deleteAuthor.execute(authorId);

    return ResponseEntity.noContent().build();
  }
}
