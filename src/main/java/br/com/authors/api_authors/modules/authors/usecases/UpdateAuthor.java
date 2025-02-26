package br.com.authors.api_authors.modules.authors.usecases;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.dtos.UpdateAuthorDTO;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.exceptions.InvalidAgeException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class UpdateAuthor {
  private final int VALID_AGE_IN_MONTHS = 216; // 18 years in months

  private AuthorsRepository authorsRepository;

  private SaveAddress saveAddress;

  public UpdateAuthor(AuthorsRepository authorsRepository, SaveAddress saveAddress) {
    this.authorsRepository = authorsRepository;
    this.saveAddress = saveAddress;
  }

  public void execute(UpdateAuthorDTO data) {
    var author = this.authorsRepository.findById(data.address().authorId()).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    var authorAlreadyRegistered = this.authorsRepository.findByEmail(data.email());

    if (authorAlreadyRegistered.isPresent() && authorAlreadyRegistered.get().getId() != author.getId()) {
      throw new AuthorAlreadyRegisteredException();
    }

    var birthdate = LocalDate.parse(data.birthdate());

    var months = ChronoUnit.MONTHS.between(birthdate, LocalDate.now());

    if (months < this.VALID_AGE_IN_MONTHS) {
      throw new InvalidAgeException();
    }

    this.saveAddress.execute(data.address());

    author.setName(data.name());
    author.setEmail(data.email());
    author.setBirthdate(birthdate);

    this.authorsRepository.save(author);
  }
}
