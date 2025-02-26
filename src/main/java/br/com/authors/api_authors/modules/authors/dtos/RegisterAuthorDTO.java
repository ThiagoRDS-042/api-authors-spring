package br.com.authors.api_authors.modules.authors.dtos;

import br.com.authors.api_authors.utils.ValidPassword;
import br.com.authors.api_authors.utils.ValidBirthdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterAuthorDTO(
                @NotBlank String name,
                @NotBlank @Email String email,
                @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String password,
                @NotBlank @Pattern(regexp = ValidBirthdate.FORMAT, message = ValidBirthdate.MESSAGE) String birthdate) {
}
