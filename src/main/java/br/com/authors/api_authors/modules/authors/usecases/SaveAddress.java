package br.com.authors.api_authors.modules.authors.usecases;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.dtos.SaveAddressDTO;
import br.com.authors.api_authors.modules.authors.entities.Address;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.repositories.AddressesRepository;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class SaveAddress {
  private AuthorsRepository authorsRepository;

  private AddressesRepository addressesRepository;

  public SaveAddress(AddressesRepository addressesRepository, AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
    this.addressesRepository = addressesRepository;
  }

  public Address execute(SaveAddressDTO data) {
    var author = this.authorsRepository.findById(data.authorId()).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    var address = new Address(data.city(), data.street(), data.zipCode(), data.stateCode(), data.complement(),
        data.neighborhood(), data.authorId(), author);

    var addressAlreadyRegistered = this.addressesRepository.findByAuthorId(data.authorId());

    if (addressAlreadyRegistered.isPresent()) {
      address.setId(addressAlreadyRegistered.get().getId());
    }

    this.addressesRepository.save(address);

    return address;
  }
}
