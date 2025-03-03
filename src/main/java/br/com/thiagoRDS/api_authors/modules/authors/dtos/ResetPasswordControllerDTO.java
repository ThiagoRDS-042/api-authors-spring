package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import br.com.thiagoRDS.api_authors.utils.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResetPasswordControllerDTO(
                @NotBlank @Pattern(regexp = ValidPassword.FORMAT, message = ValidPassword.MESSAGE) String password) {
}
