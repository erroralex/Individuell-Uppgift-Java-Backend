package com.notnilsson.individuelluppgiftjavabackend.dto;

/**
 * <h2>MemberRequestDTO</h2>
 * <p>Ett record som hanterar data för en medlem</p>
 */
public record MemberPublicDTO(
        String firstName,
        String lastName,
        AddressDTO address,
        String email,
        String phone
) {
}
