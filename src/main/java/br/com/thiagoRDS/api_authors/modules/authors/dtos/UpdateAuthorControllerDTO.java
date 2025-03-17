package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import br.com.thiagoRDS.api_authors.utils.SwaggerAuthorExample;
import br.com.thiagoRDS.api_authors.utils.ValidBirthdate;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateAuthorControllerDTO(
                @Schema(example = SwaggerAuthorExample.NAME, requiredMode = RequiredMode.REQUIRED) @NotBlank String name,
                @Schema(example = SwaggerAuthorExample.EMAIL, requiredMode = RequiredMode.REQUIRED) @NotBlank @Email String email,
                @Schema(requiredMode = RequiredMode.REQUIRED) @NotNull AddressDTO address,
                @Schema(example = SwaggerAuthorExample.BIRTHDATE, requiredMode = RequiredMode.REQUIRED) @NotBlank @Pattern(regexp = ValidBirthdate.FORMAT, message = ValidBirthdate.MESSAGE) String birthdate) {
}
