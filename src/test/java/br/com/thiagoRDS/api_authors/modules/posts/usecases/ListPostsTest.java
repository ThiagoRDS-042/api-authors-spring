package br.com.thiagoRDS.api_authors.modules.posts.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.ListPostsDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;
import br.com.thiagoRDS.api_authors.providers.GenericWhereSpecificationProvider.GerericWhereSpecificationProvider;
import br.com.thiagoRDS.api_authors.providers.GenericWhereSpecificationProvider.Impl.GerericWhereSpecificationProviderImpl;

@ExtendWith(MockitoExtension.class)
public class ListPostsTest {
        @InjectMocks
        private ListPosts listPosts;

        @Mock
        private PostsRepository postsRepository;

        @Mock
        private GerericWhereSpecificationProvider<Post> gerericWhereSpecificationProvider;

        @Test
        @DisplayName("Should be able to list all posts")
        public void listPosts() {
                Post post = MakePost.POST.clone();
                Author author = MakeAuthor.AUTHOR.clone();

                Integer pageSize = 2;

                ListPostsDTO listPosts = new ListPostsDTO(
                                post.getTitle(),
                                post.getKeywords(),
                                post.getDescription(),
                                post.getContent(),
                                author.getEmail(),
                                author.getTag(),
                                0,
                                pageSize);

                Sort sortBy = Sort.by("up").descending().and(Sort.by("publishedAt").descending());

                Pageable pageable = PageRequest.of(listPosts.page(), listPosts.pageSize(), sortBy);

                GerericWhereSpecificationProvider<Post> gerericWhere = new GerericWhereSpecificationProviderImpl<Post>();

                Specification<Post> titleLike = gerericWhere.like(listPosts.title(),
                                "title");
                Specification<Post> keywordLike = gerericWhere.like(listPosts.keywords(),
                                "keywords");
                Specification<Post> contentLike = gerericWhere.like(listPosts.content(),
                                "content");
                Specification<Post> descriptionLike = gerericWhere.like(listPosts.description(),
                                "description");
                Specification<Post> authorEmailLike = gerericWhere.like(listPosts.authorEmail(),
                                "author.email");
                Specification<Post> authorTagLike = gerericWhere.like(listPosts.authorTag(),
                                "author.tag");
                Specification<Post> publishedNotNull = gerericWhere.notNull("publishedAt");

                when(this.gerericWhereSpecificationProvider.like(listPosts.title(),
                                "title")).thenReturn(titleLike);
                when(this.gerericWhereSpecificationProvider.like(listPosts.keywords(),
                                "keywords")).thenReturn(keywordLike);
                when(this.gerericWhereSpecificationProvider.like(listPosts.content(),
                                "content")).thenReturn(contentLike);
                when(this.gerericWhereSpecificationProvider.like(listPosts.description(),
                                "description")).thenReturn(descriptionLike);
                when(this.gerericWhereSpecificationProvider.like(listPosts.authorEmail(),
                                "author.email")).thenReturn(authorEmailLike);
                when(this.gerericWhereSpecificationProvider.like(listPosts.authorTag(),
                                "author.tag")).thenReturn(authorTagLike);
                when(this.gerericWhereSpecificationProvider.notNull("publishedAt")).thenReturn(publishedNotNull);
                when(this.postsRepository.findAll(Mockito.<Specification<Post>>any(), Mockito.eq(pageable)))
                                .thenReturn(new PageImpl<Post>(List.of(post, post), pageable, pageSize));

                List<Post> posts = this.listPosts.execute(listPosts);

                assertThat(posts).hasSize(pageSize);
                assertThat(posts).isEqualTo(List.of(post, post));
        }
}
