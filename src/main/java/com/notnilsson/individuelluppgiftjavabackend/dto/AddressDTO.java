package com.notnilsson.individuelluppgiftjavabackend.dto;

/**
 * <h2>AddressDTO</h2>
 * <p>Ett record som hanterar data för en adress</p>
 */
public record AddressDTO(
        String street,
        String city,
        String postalCode
) {
}
