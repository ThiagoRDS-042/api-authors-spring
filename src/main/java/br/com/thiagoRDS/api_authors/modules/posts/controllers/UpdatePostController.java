package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.UpdatePostControllerDTO;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.UpdatePostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.usecases.UpdatePost;
import br.com.thiagoRDS.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/posts")
public class UpdatePostController {
  @Autowired
  private UpdatePost updatePost;

  @PutMapping("/{postId}")
  public ResponseEntity<Object> update(HttpServletRequest request,
      @Valid @RequestBody UpdatePostControllerDTO data, @Valid @PathVariable UUID postId) {
    Object authorId = request.getAttribute(SessionId.ID);

    UpdatePostDTO postData = new UpdatePostDTO(
        postId,
        data.title(),
        data.content(),
        data.description(),
        data.keywords(),
        UUID.fromString(authorId.toString()));

    this.updatePost.execute(postData);

    return ResponseEntity.noContent().build();
  }
}
