package br.com.authors.api_authors.modules.authors.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterAuthorDTO(
                @NotBlank String name,
                @NotBlank @Email String email,
                @NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$", message = "Invalid password format") String password,
                @NotBlank @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format") String birthdate) {
}
