package com.nilsson.wigellApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MemberWithAccountCreateDto(
        @NotBlank @Size(max = 25) String firstName,
        @NotBlank @Size(max = 30) String lastName,
        @NotNull AddressCreateDto address,
        @NotBlank @Size(max = 100) String email,
        @Size(max = 20) String phoneNumber,
        @NotNull LocalDate dateOfBirth,
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Size(min = 3, max = 60) String password
) {
}
