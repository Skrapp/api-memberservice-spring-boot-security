package com.nilsson.wigellApi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record MemberWithAccountCreateDto(
        @NotBlank @Size(max = 25) String firstName,
        @NotBlank @Size(max = 30) String lastName,
        @NotNull @Valid AddressCreateDto address,
        @NotBlank @Email @Size(max = 100) String email,
        //ser till så att rätt format används för telefonnummer ev. plus i början, får innehålla siffror, blanksteg, bindestreck och paranteser
        @Pattern(regexp = "^\\+?[0-9\\s-()]+$") @Size(max = 20) String phoneNumber,
        @NotNull @Past LocalDate dateOfBirth,
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Size(min = 3, max = 60) String password
) {
}
