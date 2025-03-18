package br.com.thiagoRDS.api_authors.modules.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.PostWithouAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.posts.dtos.CreatePostDTO;
import br.com.thiagoRDS.api_authors.modules.posts.entities.Post;

public record MakePost() {
    public static final Post POST = new Post(
            UUID.randomUUID(),
            "title",
            "content",
            "description",
            "keywords;keywords",
            0,
            1,
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            UUID.randomUUID(),
            new Author());

    public static final CreatePostDTO CREATE_POST_DTO = new CreatePostDTO(
            POST.getTitle(),
            POST.getContent(),
            POST.getDescription(),
            Stream.of(POST.getKeywords().split(";")).collect(Collectors.toList()),
            POST.getAuthorId());

    public static final PostWithouAuthorDTO POST_WITHOUT_AUTHOR = new PostWithouAuthorDTO(
            POST.getId(),
            POST.getTitle(),
            POST.getContent(),
            POST.getDescription(),
            POST.getKeywords(),
            POST.getUp(),
            POST.getPublishedAt());

    public static final List<PostWithouAuthorDTO> POSTS_WITHOUT_AUTHOR = List.of(POST_WITHOUT_AUTHOR);
}
