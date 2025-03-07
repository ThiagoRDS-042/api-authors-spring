package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AddressDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.UpdateAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.InvalidAgeException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AddressesRepository;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAddress;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;

@ExtendWith(MockitoExtension.class)
public class UpdateAuthorTest {
        @InjectMocks
        private UpdateAuthor updateAuthor;

        @Mock
        private AuthorsRepository authorsRepository;

        @Mock
        private AddressesRepository addressesRepository;

        @Test
        @DisplayName("Should be able to update a author account and create address")
        public void updateAuthor() {
                Author author = MakeAuthor.AUTHOR;
                Address address = MakeAddress.ADDRESS;
                address.setId(null);
                author.setBirthdate(LocalDate.now().minusYears(19));

                AddressDTO addressDTO = new AddressDTO(
                                address.getCity(),
                                address.getStreet(),
                                address.getZipCode(),
                                address.getStateCode(),
                                address.getComplement(),
                                address.getNeighborhood());

                UpdateAuthorDTO updateAuthor = new UpdateAuthorDTO(
                                author.getId(),
                                author.getName(),
                                author.getEmail(),
                                addressDTO,
                                author.getBirthdate().toString());

                when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));
                when(this.authorsRepository.findByEmail(author.getEmail())).thenReturn(Optional.empty());
                when(this.addressesRepository.save(address)).thenReturn(address);

                assertThatCode(() -> this.updateAuthor.execute(updateAuthor)).doesNotThrowAnyException();
                assertThat(author.getUpdtaedAt()).isInstanceOf(LocalDateTime.class);
        }

        @Test
        @DisplayName("Should not be able to update a author account with non-existing author")
        public void authorNotFound() {
                Author author = MakeAuthor.AUTHOR;
                Address address = MakeAddress.ADDRESS;

                AddressDTO addressDTO = new AddressDTO(
                                address.getCity(),
                                address.getStreet(),
                                address.getZipCode(),
                                address.getStateCode(),
                                address.getComplement(),
                                address.getNeighborhood());

                UpdateAuthorDTO updateAuthor = new UpdateAuthorDTO(
                                author.getId(),
                                author.getName(),
                                author.getEmail(),
                                addressDTO,
                                author.getBirthdate().toString());

                when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.empty());

                assertThatThrownBy(() -> this.updateAuthor.execute(updateAuthor))
                                .isInstanceOf(AuthorNotFoundException.class);
        }

        @Test
        @DisplayName("Should not be able to update a author account with same email another author")
        public void authorAlreadyRegistered() {
                Author author = MakeAuthor.AUTHOR;
                Address address = MakeAddress.ADDRESS;
                Author anotherAuthor = new Author(
                                UUID.randomUUID(),
                                "author",
                                "another-author@example.com",
                                "Test-123",
                                "Test123",
                                "avatar.png",
                                LocalDate.now().minusYears(19),
                                UUID.randomUUID(),
                                new Address(),
                                LocalDateTime.now(),
                                LocalDateTime.now());

                AddressDTO addressDTO = new AddressDTO(
                                address.getCity(),
                                address.getStreet(),
                                address.getZipCode(),
                                address.getStateCode(),
                                address.getComplement(),
                                address.getNeighborhood());

                UpdateAuthorDTO updateAuthor = new UpdateAuthorDTO(
                                author.getId(),
                                author.getName(),
                                anotherAuthor.getEmail(),
                                addressDTO,
                                author.getBirthdate().toString());

                when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));
                when(this.authorsRepository.findByEmail(anotherAuthor.getEmail()))
                                .thenReturn(Optional.of(anotherAuthor));

                assertThatThrownBy(() -> this.updateAuthor.execute(updateAuthor))
                                .isInstanceOf(AuthorAlreadyRegisteredException.class);
        }

        @Test
        @DisplayName("Should be able to update a author account with a invalid age")
        public void invalidAge() {
                Author author = MakeAuthor.AUTHOR;
                Address address = MakeAddress.ADDRESS;
                author.setBirthdate(LocalDate.now().minusYears(10));

                AddressDTO addressDTO = new AddressDTO(
                                address.getCity(),
                                address.getStreet(),
                                address.getZipCode(),
                                address.getStateCode(),
                                address.getComplement(),
                                address.getNeighborhood());

                UpdateAuthorDTO updateAuthor = new UpdateAuthorDTO(
                                author.getId(),
                                author.getName(),
                                author.getEmail(),
                                addressDTO,
                                author.getBirthdate().toString());

                when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));

                assertThatThrownBy(() -> this.updateAuthor.execute(updateAuthor))
                                .isInstanceOf(InvalidAgeException.class);
        }
}
