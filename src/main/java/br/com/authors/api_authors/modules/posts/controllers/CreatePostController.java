package br.com.authors.api_authors.modules.posts.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.authors.api_authors.modules.posts.dtos.CreatePostControllerDTO;
import br.com.authors.api_authors.modules.posts.dtos.CreatePostDTO;
import br.com.authors.api_authors.modules.posts.dtos.PostResponseMapperDTO;
import br.com.authors.api_authors.modules.posts.entities.Post;
import br.com.authors.api_authors.modules.posts.mappers.PostMapper;
import br.com.authors.api_authors.modules.posts.usecases.CreatePost;
import br.com.authors.api_authors.utils.SessionId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/posts")
public class CreatePostController {
  @Autowired
  private CreatePost createPost;

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
