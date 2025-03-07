package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.DeletePostByIdDTO;
import br.com.thiagoRDS.api_authors.modules.posts.usecases.DeletePost;
import br.com.thiagoRDS.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/posts")
public class DeletePostController {
  @Autowired
  private DeletePost deletePostById;

  @DeleteMapping("/{postId}")
  public ResponseEntity<Object> delete(HttpServletRequest request, @Valid @PathVariable UUID postId) {
    Object authorId = request.getAttribute(SessionId.ID);

    DeletePostByIdDTO deletePostData = new DeletePostByIdDTO(UUID.fromString(authorId.toString()), postId);

    this.deletePostById.execute(deletePostData);

    return ResponseEntity.noContent().build();
  }
}
