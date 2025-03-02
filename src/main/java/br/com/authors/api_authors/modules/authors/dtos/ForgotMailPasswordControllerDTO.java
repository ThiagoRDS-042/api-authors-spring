package br.com.authors.api_authors.modules.authors.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotMailPasswordControllerDTO(@NotBlank @Email String email) {
}
