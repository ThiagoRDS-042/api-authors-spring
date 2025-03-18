package br.com.thiagoRDS.api_authors.modules.authors.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.ListAuthorsDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.mappers.AuthorMapper;
import br.com.thiagoRDS.api_authors.modules.authors.usecases.ListAuthors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Author", description = "Manage authors")
@RestController
@RequestMapping("/authors")
public class ListAuthorsController {
  @Autowired
  private ListAuthors listAuthors;

  @Operation(summary = "List all authors", description = "List all authors")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = AuthorReponseMapperDTO.class)))
      }, description = "Success"),
  })
  @GetMapping("")
  public ResponseEntity<List<AuthorReponseMapperDTO>> list(@RequestParam(required = false) String email,
      @RequestParam(required = false) String tag, @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

    ListAuthorsDTO filters = new ListAuthorsDTO(email, tag, page, pageSize);

    List<Author> authors = this.listAuthors.execute(filters);

    List<AuthorReponseMapperDTO> authorsMapper = authors.stream().map(AuthorMapper::ToHttp).toList();

    return ResponseEntity.ok(authorsMapper);
  }
}
