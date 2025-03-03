package br.com.thiagoRDS.api_authors.providers.dtos;

public record SignResponseDTO(
        String token,
        Long expiresIn) {
}
