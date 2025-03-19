package br.com.thiagoRDS.api_authors.providers.MailProvider.dtos;

public record SendMailDTO(
                String to,
                String subject,
                String text) {
}
