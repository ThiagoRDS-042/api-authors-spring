package br.com.authors.api_authors.modules.authors.dtos;

public record ListAuthorsDTO(String email, String tag, Integer page, Integer pageSize) {
}
