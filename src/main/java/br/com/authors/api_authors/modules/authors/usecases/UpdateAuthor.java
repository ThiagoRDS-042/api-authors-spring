package br.com.authors.api_authors.modules.authors.usecases;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.dtos.UpdateAuthorDTO;
import br.com.authors.api_authors.modules.authors.entities.Address;
import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.exceptions.InvalidAgeException;
import br.com.authors.api_authors.modules.authors.repositories.AddressesRepository;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.authors.api_authors.utils.ValidAge;

@Service
public class UpdateAuthor {
  private final AuthorsRepository authorsRepository;
  private final AddressesRepository addressesRepository;

  public UpdateAuthor(AuthorsRepository authorsRepository, AddressesRepository addressesRepository) {
    this.authorsRepository = authorsRepository;
    this.addressesRepository = addressesRepository;
  }

  public void execute(UpdateAuthorDTO data) {
    Author author = this.authorsRepository.findById(data.authorId()).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    Optional<Author> authorAlreadyRegistered = this.authorsRepository.findByEmail(data.email());

    if (authorAlreadyRegistered.isPresent() && !authorAlreadyRegistered.get().getId().equals(author.getId())) {
      throw new AuthorAlreadyRegisteredException();
    }

    LocalDate birthdate = LocalDate.parse(data.birthdate());

    Long months = ChronoUnit.MONTHS.between(birthdate, LocalDate.now());

    if (months < ValidAge.VALUE) {
      throw new InvalidAgeException();
    }

    Address address = new Address(
        data.address().city(),
        data.address().street(),
        data.address().zipCode(),
        data.address().stateCode(),
        data.address().complement(),
        data.address().neighborhood());

    if (author.getAddressId() != null) {
      address.setId(author.getAddressId());
    }

    address = this.addressesRepository.save(address);

    author.setAddressId(address.getId());
    author.setName(data.name());
    author.setEmail(data.email());
    author.setBirthdate(birthdate);
    author.setUpdtaedAt(LocalDateTime.now());

    this.authorsRepository.save(author);
  }
}
