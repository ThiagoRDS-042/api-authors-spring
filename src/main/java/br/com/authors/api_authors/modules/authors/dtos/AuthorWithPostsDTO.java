package br.com.authors.api_authors.modules.authors.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.authors.api_authors.modules.authors.entities.Address;
import br.com.authors.api_authors.modules.authors.entities.Author;

public class AuthorWithPostsDTO extends Author {
  public AuthorWithPostsDTO(UUID id, String name, String email, String tag, String password, LocalDate birthdate,
      UUID addressId,
      Address address,
      LocalDateTime updtaedAt, LocalDateTime createdAt, List<PostWithouAuthorDTO> posts) {
    super(id, name, email, tag, password, birthdate, addressId, address, updtaedAt, createdAt);
    this.posts = posts;
  }

  private List<PostWithouAuthorDTO> posts;

  public List<PostWithouAuthorDTO> getPosts() {
    return this.posts;
  }

  public void setPosts(List<PostWithouAuthorDTO> posts) {
    this.posts = posts;
  }
}
