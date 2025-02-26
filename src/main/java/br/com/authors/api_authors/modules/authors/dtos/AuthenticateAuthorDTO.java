package br.com.authors.api_authors.modules.authors.dtos;

import br.com.authors.api_authors.utils.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AuthenticateAuthorDTO(
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String password) {
}
