package br.com.thiagoRDS.api_authors.modules.authors.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;

@Repository
public interface AddressesRepository extends JpaRepository<Address, UUID> {
}
