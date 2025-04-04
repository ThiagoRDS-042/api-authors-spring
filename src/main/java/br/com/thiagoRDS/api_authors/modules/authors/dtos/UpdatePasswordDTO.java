package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import java.util.UUID;

import br.com.thiagoRDS.api_authors.utils.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePasswordDTO(
        @NotBlank UUID authorId,
        @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String oldPassword,
        @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String newPassword,
        @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String confirmpassword) {
}
