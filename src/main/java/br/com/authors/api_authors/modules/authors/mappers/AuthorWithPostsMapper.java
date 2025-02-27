package br.com.authors.api_authors.modules.authors.mappers;

import br.com.authors.api_authors.modules.authors.dtos.AuthorWithPostsDTO;
import br.com.authors.api_authors.modules.authors.dtos.AuthorWithPostsReponseMapperDTO;

public record AuthorWithPostsMapper() {
  public static AuthorWithPostsReponseMapperDTO ToHttp(AuthorWithPostsDTO authorWithPosts) {
    AuthorWithPostsReponseMapperDTO authorMapper = new AuthorWithPostsReponseMapperDTO(
        authorWithPosts.getId(),
        authorWithPosts.getTag(),
        authorWithPosts.getName(),
        authorWithPosts.getEmail(),
        authorWithPosts.getAddress(),
        authorWithPosts.getBirthdate(),
        authorWithPosts.getPosts());

    return authorMapper;
  }
}
