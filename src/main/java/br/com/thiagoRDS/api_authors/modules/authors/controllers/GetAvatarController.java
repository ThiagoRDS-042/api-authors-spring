package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.GetAvatar;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Author", description = "Manage authors")
@RestController
@RequestMapping("/authors")
public class GetAvatarController {

    @Autowired
    private GetAvatar getAvatar;

    @Operation(summary = "Get a author avatar", description = "Get a author avatar")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(implementation = InputStreamResource.class))
            }, description = "Success"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
            }, description = "Author does not exists."),
    })
    @GetMapping("/avatar/{avatar:.+}")
    public ResponseEntity<Object> getAvatar(@PathVariable String avatar) {
        InputStream file = this.getAvatar.execute(avatar);

        InputStreamResource inputStreamResource = new InputStreamResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + avatar)
                .body(inputStreamResource);
    }
}
