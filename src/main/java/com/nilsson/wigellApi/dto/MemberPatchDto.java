package com.nilsson.wigellApi.dto;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MemberPatchDto(
        @Size(max = 25) String firstName,
        @Size(max = 30) String lastName,
        AddressCreateDto address,
        @Size(max = 100) String email,
        @Size(max = 20) String phoneNumber,
        LocalDate dateOfBirth
) {
}
