package br.com.authors.api_authors.providers.dtos;

public record SignResponseDTO(
    String token,
    Long expiresIn) {
}
