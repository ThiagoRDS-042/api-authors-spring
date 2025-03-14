package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.posts.dtos.ListPostsDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.providers.GerericWhereSpecificationProvider;

@Service
public class ListPosts {
        private final PostsRepository postsRepository;
        private final GerericWhereSpecificationProvider<Post> gerericWhereSpecificationProvider;

        public ListPosts(PostsRepository postsRepository,
                        GerericWhereSpecificationProvider<Post> gerericWhereSpecificationProvider) {
                this.postsRepository = postsRepository;
                this.gerericWhereSpecificationProvider = gerericWhereSpecificationProvider;
        }

        public List<Post> execute(ListPostsDTO filters) {
                Sort sortBy = Sort.by("up").descending().and(Sort.by("publishedAt").descending());
                Pageable pageable = PageRequest.of(filters.page(), filters.pageSize(), sortBy);

                Specification<Post> titleLike = this.gerericWhereSpecificationProvider.like(filters.title(),
                                "title");
                Specification<Post> keywordLike = this.gerericWhereSpecificationProvider.like(filters.keywords(),
                                "keywords");
                Specification<Post> contentLike = this.gerericWhereSpecificationProvider.like(filters.content(),
                                "content");
                Specification<Post> descriptionLike = this.gerericWhereSpecificationProvider.like(
                                filters.description(),
                                "description");
                Specification<Post> authorEmailLike = this.gerericWhereSpecificationProvider.like(
                                filters.authorEmail(),
                                "author.email");
                Specification<Post> authorTagLike = this.gerericWhereSpecificationProvider.like(
                                filters.authorTag(),
                                "author.tag");
                Specification<Post> publishedNotNull = this.gerericWhereSpecificationProvider.notNull(
                                "publishedAt");

                Page<Post> posts = this.postsRepository.findAll(
                                titleLike.and(keywordLike).and(contentLike).and(descriptionLike).and(authorEmailLike)
                                                .and(authorTagLike)
                                                .and(publishedNotNull),
                                pageable);

                return posts.getContent();
        }
}
