package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorMessageDTO;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.RegisterAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.mappers.AuthorMapper;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.RegisterAuthor;
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
public class RegisterAuthorController {

    @Autowired
    private RegisterAuthor registerAuthor;

    @Operation(summary = "Register a new author", description = "Register a new author")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthorReponseMapperDTO.class))
            }, description = "Success"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class)))
            }, description = "Validation failed."),
            @ApiResponse(responseCode = "409", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
            }, description = "Author already registered."),
            @ApiResponse(responseCode = "422", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
            }, description = "Minimum age not reached."),
    })
    @PostMapping()
    public ResponseEntity<AuthorReponseMapperDTO> register(@Valid @RequestBody RegisterAuthorDTO data) {
        Author author = this.registerAuthor.execute(data);

        AuthorReponseMapperDTO authorMapper = AuthorMapper.ToHttp(author);

        return ResponseEntity.ok(authorMapper);
    }
}
