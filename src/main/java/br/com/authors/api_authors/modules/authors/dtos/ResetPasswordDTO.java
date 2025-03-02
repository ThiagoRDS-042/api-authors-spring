package br.com.authors.api_authors.modules.authors.dtos;

import java.util.UUID;

import br.com.authors.api_authors.utils.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResetPasswordDTO(
    @NotBlank UUID code,
    @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String password) {
}
