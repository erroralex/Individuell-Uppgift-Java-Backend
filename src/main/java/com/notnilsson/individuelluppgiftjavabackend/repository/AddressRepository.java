package com.notnilsson.individuelluppgiftjavabackend.repository;

import com.notnilsson.individuelluppgiftjavabackend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <h2>AddressRepository</h2>
 * <p>No-code repository som ärver från {@code JpaRepository} för hantering av adressentiteter.</p>
 * <p>Tillhandahåller CRUD-operationer</p>
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByStreetAndCityAndPostalCode(String street, String city, String postalCode);

}
