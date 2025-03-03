package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import java.util.UUID;

import br.com.thiagoRDS.api_authors.utils.ValidBirthdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateAuthorDTO(
                @NotBlank UUID authorId,
                @NotBlank String name,
                @NotBlank @Email String email,
                @NotNull AddressDTO address,
                @NotBlank @Pattern(regexp = ValidBirthdate.FORMAT, message = ValidBirthdate.MESSAGE) String birthdate) {
}
