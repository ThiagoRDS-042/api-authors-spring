package br.com.authors.api_authors.modules.authors.dtos;

public record AuthenticateAuthorResponseDTO(
    String token,
    Long expiresIn) {
}
