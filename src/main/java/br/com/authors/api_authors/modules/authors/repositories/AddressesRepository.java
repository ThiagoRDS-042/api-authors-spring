package br.com.authors.api_authors.modules.authors.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.authors.api_authors.modules.authors.entities.Address;

public interface AddressesRepository extends JpaRepository<Address, UUID> {
}
