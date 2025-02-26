package br.com.authors.api_authors.modules.authors.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;

public record SaveAddressDTO(
    @NotEmpty String city,
    @NotEmpty String street,
    @NotEmpty String zipCode,
    @NotEmpty String stateCode,
    String complement,
    @NotEmpty String neighborhood,
    @NotEmpty UUID authorId) {
}
