package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import java.util.UUID;

import br.com.thiagoRDS.api_authors.utils.SwaggerAddressExample;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record AddressResponseMapperDTO(
    @Schema(requiredMode = RequiredMode.REQUIRED) UUID id,
    @Schema(example = SwaggerAddressExample.CITY, requiredMode = RequiredMode.REQUIRED) String city,
    @Schema(example = SwaggerAddressExample.STREET, requiredMode = RequiredMode.REQUIRED) String street,
    @Schema(example = SwaggerAddressExample.ZIP_CODE, requiredMode = RequiredMode.REQUIRED) String zipCode,
    @Schema(example = SwaggerAddressExample.STATE_CODE, requiredMode = RequiredMode.REQUIRED) String stateCode,
    @Schema(example = SwaggerAddressExample.COMPLEMENT, requiredMode = RequiredMode.REQUIRED) String complement,
    @Schema(example = SwaggerAddressExample.NEIGHBORHOOD, requiredMode = RequiredMode.REQUIRED) String neighborhood) {
}
