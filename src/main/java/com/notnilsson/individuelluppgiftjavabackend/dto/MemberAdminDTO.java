package com.notnilsson.individuelluppgiftjavabackend.dto;

import java.time.LocalDate;

/**
 * <h2>MemberAdminDTO</h2>
 * <p>Ett record som hanterar data för en admin</p>
 */
public record MemberAdminDTO(
        long id,
        String firstName,
        String lastName,
        AddressDTO address,
        String email,
        String phone,
        LocalDate dateOfBirth
) {
}
