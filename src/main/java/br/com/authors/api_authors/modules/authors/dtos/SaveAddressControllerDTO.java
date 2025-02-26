package br.com.authors.api_authors.modules.authors.dtos;

import br.com.authors.api_authors.utils.ValidStateCode;
import br.com.authors.api_authors.utils.ValidZipCode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record SaveAddressControllerDTO(
                @NotEmpty String city,
                @NotEmpty String street,
                @NotEmpty @Pattern(regexp = ValidZipCode.FORMAT, message = ValidZipCode.MESSAGE) String zipCode,
                @NotEmpty @Pattern(regexp = ValidStateCode.FORMAT, message = ValidStateCode.MESSAGE) String stateCode,
                String complement,
                @NotEmpty String neighborhood) {
}
