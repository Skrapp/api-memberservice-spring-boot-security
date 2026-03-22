package com.nilsson.wigellApi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MemberUpdateDto(
        @NotBlank @Size(max = 25) String firstName,
        @NotBlank @Size(max = 30) String lastName,
        @NotNull @Valid AddressCreateDto address,
        @Email @NotBlank @Size(max = 100) String email,
        //ser till så att rätt format används för telefonnummer ev. plus i början, får innehålla siffror, blanksteg, bindestreck och paranteser
        @Pattern(regexp = "^\\+?[0-9\\s-()]+$") @Size(max = 20) String phoneNumber,
        @Past @NotNull LocalDate dateOfBirth
) {
}