package com.nilsson.wigellApi.dto;

import java.time.LocalDate;

public record MemberDto(
        Long id,
        String firstName,
        String lastName,
        AddressDto address,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth
) {
}
