package br.com.authors.api_authors.modules.authors.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record SaveAddressControllerDTO(
        @NotEmpty String city,
        @NotEmpty String street,
        @NotEmpty @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "invalid zip code format") String zipCode,
        @NotEmpty @Pattern(regexp = "[A-Z]{2}", message = "invalid state code format") String stateCode,
        String complement,
        @NotEmpty String neighborhood) {
}
