package br.com.authors.api_authors.modules.authors.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateAuthorControllerDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotNull SaveAddressControllerDTO address,
        @NotBlank @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format") String birthdate) {
}
