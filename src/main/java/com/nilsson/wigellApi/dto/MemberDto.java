package com.nilsson.wigellApi.dto;

import java.time.LocalDate;

public record MemberDto(
        Long id,
        String username,
        String firstName,
        String lastName,
        AddressDto address,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth
) {
}
