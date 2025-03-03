package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.PublishPostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.usecases.PublishPost;
import br.com.thiagoRDS.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/posts")
public class PublishPostController {
  @Autowired
  private PublishPost publishPost;

  @PatchMapping("/{postId}/publish")
  public ResponseEntity<Object> publish(HttpServletRequest request, @Valid @PathVariable UUID postId) {
    Object authorId = request.getAttribute(SessionId.ID);

    PublishPostDTO postData = new PublishPostDTO(UUID.fromString(authorId.toString()), postId);

    this.publishPost.execute(postData);

    return ResponseEntity.noContent().build();
  }
}
