package br.com.authors.api_authors.modules.authors.dtos;

import br.com.authors.api_authors.utils.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePasswordControllerDTO(
        @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String oldPassword,
        @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String newPassword,
        @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String confirmpassword) {
}
