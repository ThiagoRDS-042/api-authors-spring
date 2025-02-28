package br.com.authors.api_authors.modules.posts.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.posts.usecases.UpPost;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/posts")
public class UpPostController {
  @Autowired
  private UpPost upPost;

  @PatchMapping("/{postId}/up")
  public ResponseEntity<Object> prive(@Valid @PathVariable UUID postId) {
    this.upPost.execute(postId);

    return ResponseEntity.noContent().build();
  }
}
