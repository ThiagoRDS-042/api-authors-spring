package br.com.thiagoRDS.api_authors.modules.posts.mappers;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.authors.mappers.AuthorMapper;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.PostResponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;

public class PostMapper {
  public static PostResponseMapperDTO ToHttp(Post post) {
    AuthorReponseMapperDTO author = AuthorMapper.ToHttp(post.getAuthor());

    PostResponseMapperDTO authorMapper = new PostResponseMapperDTO(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getDescription(),
        post.getKeywords().split(";"),
        author,
        post.getUp(),
        post.getPublishedAt());

    return authorMapper;
  }
}
