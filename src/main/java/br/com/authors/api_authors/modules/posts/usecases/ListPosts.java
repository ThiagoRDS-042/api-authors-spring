package br.com.authors.api_authors.modules.posts.usecases;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.posts.dtos.ListPostsDTO;
import br.com.authors.api_authors.modules.posts.entities.Post;
import br.com.authors.api_authors.modules.posts.repositories.PostsRepository;
import br.com.authors.api_authors.utils.GenericLikeSpecification;

@Service
public class ListPosts {
  private final PostsRepository postsRepository;

  public ListPosts(PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
  }

  public List<Post> execute(ListPostsDTO filters) {
    Pageable pageable = PageRequest.of(filters.page(), filters.pageSize(), Direction.DESC, "like");

    Specification<Post> titleLike = new GenericLikeSpecification<Post>().like(filters.title(), "title");
    Specification<Post> keywordLike = new GenericLikeSpecification<Post>().like(filters.keywords(), "keywords");
    Specification<Post> contentLike = new GenericLikeSpecification<Post>().like(filters.content(), "content");
    Specification<Post> descriptionLike = new GenericLikeSpecification<Post>().like(filters.description(),
        "description");

    Page<Post> posts = this.postsRepository.findAll(
        titleLike.and(keywordLike).and(contentLike).and(descriptionLike),
        pageable);

    return posts.getContent();
  }
}
