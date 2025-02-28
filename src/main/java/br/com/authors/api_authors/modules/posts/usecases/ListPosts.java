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
import br.com.authors.api_authors.providers.GerericWhereSpecification;

@Service
public class ListPosts {
  private final PostsRepository postsRepository;

  public ListPosts(PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
  }

  public List<Post> execute(ListPostsDTO filters) {
    Pageable pageable = PageRequest.of(filters.page(), filters.pageSize(), Direction.DESC, "up", "publishedAt");

    Specification<Post> titleLike = new GerericWhereSpecification<Post>().like(filters.title(), "title");
    Specification<Post> keywordLike = new GerericWhereSpecification<Post>().like(filters.keywords(), "keywords");
    Specification<Post> contentLike = new GerericWhereSpecification<Post>().like(filters.content(), "content");
    Specification<Post> descriptionLike = new GerericWhereSpecification<Post>().like(filters.description(),
        "description");
    Specification<Post> authorEmailLike = new GerericWhereSpecification<Post>().like(filters.authorEmail(),
        "author.email");
    Specification<Post> authorTagLike = new GerericWhereSpecification<Post>().like(filters.authorTag(),
        "author.tag");
    Specification<Post> publishedNotNull = new GerericWhereSpecification<Post>().notNull(
        "publishedAt");

    Page<Post> posts = this.postsRepository.findAll(
        titleLike.and(keywordLike).and(contentLike).and(descriptionLike).and(authorEmailLike).and(authorTagLike)
            .and(publishedNotNull),
        pageable);

    return posts.getContent();
  }
}
