package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotMailPasswordControllerDTO(@NotBlank @Email String email) {
}
