package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.ListPostsDTO;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.PostResponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.mappers.PostMapper;
import br.com.thiagoRDS.api_authors.modules.posts.usecases.ListPosts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/posts")
public class ListPostsController {
  @Autowired
  private ListPosts listPosts;

  @GetMapping("")
  public ResponseEntity<List<PostResponseMapperDTO>> list(@RequestParam(required = false) String title,
      @RequestParam(required = false) String content, @RequestParam(required = false) String authorEmail,
      @RequestParam(required = false) String authorTag, @RequestParam(required = false) String description,
      @RequestParam(required = false) String keywords, @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

    ListPostsDTO filters = new ListPostsDTO(
        title,
        keywords,
        description,
        content,
        authorEmail,
        authorTag,
        page,
        pageSize);

    List<Post> posts = this.listPosts.execute(filters);

    List<PostResponseMapperDTO> postsMapper = posts.stream().map(PostMapper::ToHttp).toList();

    return ResponseEntity.ok(postsMapper);
  }

}
