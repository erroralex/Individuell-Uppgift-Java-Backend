package com.notnilsson.individuelluppgiftjavabackend.dto;

import java.time.LocalDate;

/**
 * <h2>MemberRequestDTO</h2>
 * <p>Ett record som hanterar indata för en medlem</p>
 */
public record MemberRequestDTO(
        String firstName,
        String lastName,
        AddressDTO address,
        String email,
        String phone,
        LocalDate dateOfBirth
) {
}
