package br.com.authors.api_authors.providers.dtos;

public record SendMailDTO(
    String to,
    String subject,
    String text) {
}
