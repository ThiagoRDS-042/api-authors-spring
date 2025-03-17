package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorMessageDTO;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.ResetPasswordControllerDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.ResetPasswordDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.ResetPassword;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Author", description = "Manage authors")
@RestController
@RequestMapping("/authors")
public class ResetPasswordController {
  @Autowired
  private ResetPassword resetPassword;

  @Operation(summary = "Reset the author password", description = "Reset the author password")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Success"),
      @ApiResponse(responseCode = "400", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class)))
      }, description = "Validation failed."),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Recovery token does not exists."),
      @ApiResponse(responseCode = "403", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Recovery token expired."),
  })
  @PatchMapping("/password/reset")
  public ResponseEntity<Object> putMethodName(@RequestParam UUID code,
      @Valid @RequestBody ResetPasswordControllerDTO data) {
    ResetPasswordDTO resetPasswordData = new ResetPasswordDTO(code, data.password());

    this.resetPassword.execute(resetPasswordData);

    return ResponseEntity.noContent().build();
  }
}
