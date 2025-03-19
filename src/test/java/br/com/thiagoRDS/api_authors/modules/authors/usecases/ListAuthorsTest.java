package br.com.thiagoRDS.api_authors.modules.authors.usecases;

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

import br.com.thiagoRDS.api_authors.modules.authors.dtos.ListAuthorsDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.providers.GenericWhereSpecificationProvider.GerericWhereSpecificationProvider;
import br.com.thiagoRDS.api_authors.providers.GenericWhereSpecificationProvider.Impl.GerericWhereSpecificationProviderImpl;

@ExtendWith(MockitoExtension.class)
public class ListAuthorsTest {
        @InjectMocks
        private ListAuthors listAuthors;

        @Mock
        private AuthorsRepository authorsRepository;

        @Mock
        private GerericWhereSpecificationProvider<Author> gerericWhereSpecificationProvider;

        @Test
        @DisplayName("Should be able to list an authors by filters")
        public void listAuthors() {
                Author author = MakeAuthor.AUTHOR.clone();
                Integer pageSize = 2;

                ListAuthorsDTO listAuthors = new ListAuthorsDTO(
                                author.getEmail(),
                                author.getTag(),
                                0,
                                pageSize);

                Sort sortBy = Sort.by("createdAt").descending();

                Pageable pageable = PageRequest.of(listAuthors.page(), listAuthors.pageSize(), sortBy);

                GerericWhereSpecificationProvider<Author> gerericWhere = new GerericWhereSpecificationProviderImpl<Author>();

                Specification<Author> emailLike = gerericWhere.like(listAuthors.email(),
                                "email");
                Specification<Author> tagLike = gerericWhere.like(listAuthors.tag(), "tag");

                when(this.gerericWhereSpecificationProvider.like(listAuthors.email(),
                                "email")).thenReturn(emailLike);
                when(this.gerericWhereSpecificationProvider.like(listAuthors.tag(),
                                "tag")).thenReturn(tagLike);
                when(this.authorsRepository.findAll(Mockito.<Specification<Author>>any(),
                                Mockito.eq(pageable)))
                                .thenReturn(new PageImpl<Author>(List.of(author, author), pageable, pageSize));

                List<Author> authors = this.listAuthors.execute(listAuthors);

                assertThat(authors).hasSize(pageSize);
                assertThat(authors).isEqualTo(List.of(author, author));
        }
}
