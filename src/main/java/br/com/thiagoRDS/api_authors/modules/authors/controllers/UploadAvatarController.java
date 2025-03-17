package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.thiagoRDS.api_authors.config.SwaggerConfig;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.UploadAvatarDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.UploadAvatar;
import br.com.thiagoRDS.api_authors.utils.SessionId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PatchMapping;

@Tag(name = "Author", description = "Manage authors")
@RestController
@RequestMapping("/authors")
public class UploadAvatarController {
  @Autowired
  private UploadAvatar uploadAvatar;

  @SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
  @Operation(summary = "Upload the author avatar", description = "Upload the author avatar")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Success"),
      @ApiResponse(responseCode = "400", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Invalid file mimetype.|File too large."),
      @ApiResponse(responseCode = "404", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
      }, description = "Author does not exists."),
  })
  @PatchMapping("/avatar")
  public ResponseEntity<Object> upload(HttpServletRequest request, @RequestParam MultipartFile file) {
    Object authorId = request.getAttribute(SessionId.ID);

    UploadAvatarDTO avatarData = new UploadAvatarDTO(UUID.fromString(authorId.toString()), file);

    this.uploadAvatar.execute(avatarData);

    return ResponseEntity.noContent().build();
  }
}
