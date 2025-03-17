package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.config.SwaggerConfig;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorMessageDTO;
import br.com.thiagoRDS.api_authors.exceptions.dtos.ErrorResponseDTO;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.CreatePostControllerDTO;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.CreatePostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.PostResponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.mappers.PostMapper;
import br.com.thiagoRDS.api_authors.modules.posts.usecases.CreatePost;
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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Post", description = "Manage posts")
@RestController
@RequestMapping("/posts")
public class CreatePostController {
    @Autowired
    private CreatePost createPost;

    @SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
    @Operation(summary = "Create a new post", description = "Create a new post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostResponseMapperDTO.class)), description = "Success"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class)))
            }, description = "Validation failed."),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
            }, description = "Author does not exists."),
            @ApiResponse(responseCode = "409", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))
            }, description = "Post already registered."),
    })
    @PostMapping("")
    public ResponseEntity<PostResponseMapperDTO> create(HttpServletRequest request,
            @Valid @RequestBody CreatePostControllerDTO data) {
        Object authorId = request.getAttribute(SessionId.ID);

        CreatePostDTO postData = new CreatePostDTO(
                data.title(),
                data.content(),
                data.description(),
                data.keywords(),
                UUID.fromString(authorId.toString()));

        Post post = this.createPost.execute(postData);

        PostResponseMapperDTO postMapper = PostMapper.ToHttp(post);

        return ResponseEntity.ok(postMapper);
    }
}
