package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import br.com.thiagoRDS.api_authors.utils.SwaggerAddressExample;
import br.com.thiagoRDS.api_authors.utils.ValidStateCode;
import br.com.thiagoRDS.api_authors.utils.ValidZipCode;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(
    @Schema(example = SwaggerAddressExample.CITY, requiredMode = RequiredMode.REQUIRED) @NotEmpty String city,
    @Schema(example = SwaggerAddressExample.STREET, requiredMode = RequiredMode.REQUIRED) @NotEmpty String street,
    @Schema(example = SwaggerAddressExample.ZIP_CODE, requiredMode = RequiredMode.REQUIRED) @NotEmpty @Pattern(regexp = ValidZipCode.FORMAT, message = ValidZipCode.MESSAGE) String zipCode,
    @Schema(example = SwaggerAddressExample.STATE_CODE, requiredMode = RequiredMode.REQUIRED) @NotEmpty @Pattern(regexp = ValidStateCode.FORMAT, message = ValidStateCode.MESSAGE) String stateCode,
    @Schema(example = SwaggerAddressExample.COMPLEMENT, requiredMode = RequiredMode.NOT_REQUIRED) String complement,
    @Schema(example = SwaggerAddressExample.NEIGHBORHOOD, requiredMode = RequiredMode.REQUIRED) @NotEmpty String neighborhood) {
}
