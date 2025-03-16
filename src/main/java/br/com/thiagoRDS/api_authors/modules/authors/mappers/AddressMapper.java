package br.com.thiagoRDS.api_authors.modules.authors.mappers;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AddressResponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;

public class AddressMapper {
  public static AddressResponseMapperDTO ToHttp(Address address) {

    AddressResponseMapperDTO addressMapper = new AddressResponseMapperDTO(
        address.getId(),
        address.getCity(),
        address.getStreet(),
        address.getZipCode(),
        address.getStateCode(),
        address.getComplement(),
        address.getNeighborhood());

    return addressMapper;
  }
}
