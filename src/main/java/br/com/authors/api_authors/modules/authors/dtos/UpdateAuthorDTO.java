package br.com.authors.api_authors.modules.authors.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateAuthorDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotNull SaveAddressDTO address,
        @NotBlank @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format") String birthdate) {
}
