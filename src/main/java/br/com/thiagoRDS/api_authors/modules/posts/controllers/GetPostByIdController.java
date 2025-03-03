package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.PostResponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.mappers.PostMapper;
import br.com.thiagoRDS.api_authors.modules.posts.usecases.GetPostById;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/posts")
public class GetPostByIdController {
  @Autowired
  private GetPostById getPostById;

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponseMapperDTO> prive(@Valid @PathVariable UUID postId) {

    Post post = this.getPostById.execute(postId);

    PostResponseMapperDTO postMapper = PostMapper.ToHttp(post);

    return ResponseEntity.ok(postMapper);
  }
}
