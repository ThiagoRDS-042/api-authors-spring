package br.com.thiagoRDS.api_authors.modules.posts.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;
import br.com.thiagoRDS.api_authors.modules.posts.repositories.PostsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.modules.utils.MakePost;

@Transactional
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ListPostsControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private AuthorsRepository authorsRepository;

  @BeforeAll
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("Should be able to list all posts")
  public void listPosts() throws Exception {
    String baseTitle = "base-title";
    String baseContent = "base-content";
    String baseKeywords = "base-keywords";
    String baseDescription = "base-description";
    String baseAuthorTag = "base-author-tag";
    String baseAuthorEmail = "base-author-email";

    Author author = MakeAuthor.AUTHOR.clone();
    author.setId(null);
    author.setEmail(baseAuthorEmail + "@example.com");
    author.setTag(baseAuthorTag + "author-1");
    author = this.authorsRepository.saveAndFlush(author);

    Post post = MakePost.POST.clone();
    post.setId(null);
    post.setTitle(baseTitle + "title");
    post.setContent(baseContent + "content");
    post.setDescription(baseDescription + "description");
    post.setKeywords(baseKeywords + "keywords");
    post.setAuthorId(author.getId());
    post.setAuthor(author);

    Post anotherPost = MakePost.POST.clone();
    anotherPost.setId(null);
    anotherPost.setTitle("another title");
    anotherPost.setTitle(baseTitle + "another title");
    anotherPost.setContent(baseContent + "another content");
    anotherPost.setDescription(baseDescription + "another description");
    anotherPost.setKeywords(baseKeywords + "another keywords");
    anotherPost.setAuthorId(author.getId());
    anotherPost.setAuthor(author);

    List<Post> posts = List.of(post, anotherPost);

    this.postsRepository.saveAllAndFlush(posts);

    this.mvc.perform(get("/posts")
        .queryParam("page", "0")
        .queryParam("pageSize", "10")
        .queryParam("title", baseTitle)
        .queryParam("authorEmail", baseAuthorEmail)
        .queryParam("description", baseDescription)
        .queryParam("content", baseContent)
        .queryParam("authorTag", baseAuthorTag)
        .queryParam("keywords", baseKeywords))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }
}
