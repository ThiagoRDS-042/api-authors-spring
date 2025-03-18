package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.config.SwaggerConfig;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorMessageDTO;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.UpdatePasswordControllerDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.UpdatePasswordDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.UpdatePassword;
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

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Author", description = "Manage authors")
@RestController
@RequestMapping("/authors")
public class UpdatePasswordController {
  @Autowired
  private UpdatePassword updatePassword;

  @SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
  @Operation(summary = "Update the author password", description = "Update the author password")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Success"),
      @ApiResponse(responseCode = "400", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class)))
      }, description = "Validation failed."),
      @ApiResponse(responseCode = "401", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Invalid credentials."),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Author does not exists."),
  })
  @PatchMapping("/password")
  public ResponseEntity<Object> putMethodName(HttpServletRequest request,
      @Valid @RequestBody UpdatePasswordControllerDTO data) {
    Object authorId = request.getAttribute(SessionId.ID);

    UpdatePasswordDTO updatePasswordData = new UpdatePasswordDTO(UUID.fromString(authorId.toString()),
        data.oldPassword(),
        data.newPassword(), data.confirmpassword());

    this.updatePassword.execute(updatePasswordData);

    return ResponseEntity.noContent().build();
  }
}
