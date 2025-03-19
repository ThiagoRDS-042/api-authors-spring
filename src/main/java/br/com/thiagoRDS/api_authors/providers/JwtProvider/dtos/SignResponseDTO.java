package br.com.thiagoRDS.api_authors.providers.JwtProvider.dtos;

public record SignResponseDTO(
                String token,
                Long expiresIn) {
}
