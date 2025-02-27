package br.com.authors.api_authors.modules.authors.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostWithouAuthorDTO(
                UUID id,
                String title,
                String content,
                String description,
                String keywords,
                Integer like,
                Integer desLike,
                LocalDateTime publishedAt) {
}