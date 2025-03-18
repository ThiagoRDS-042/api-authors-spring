package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.config.SwaggerConfig;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorMessageDTO;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.AddressDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.UpdateAuthorControllerDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.UpdateAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.UpdateAuthor;
import br.com.thiagoRDS.api_authors.utils.SessionId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Author", description = "Manage authors")
@RestController
@RequestMapping("/authors")
public class UpdateAuthorController {
  @Autowired
  private UpdateAuthor updateAuthor;

  @SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
  @Operation(summary = "Update a author", description = "Update a author")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Success"),
      @ApiResponse(responseCode = "400", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class)))
      }, description = "Validation failed."),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Author does not exists."),
      @ApiResponse(responseCode = "409", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Author already registered."),
      @ApiResponse(responseCode = "422", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Minimum age not reached."),
  })
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
