package com.nilsson.wigellApi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MemberPatchDto(
        @Size(max = 25) String firstName,
        @Size(max = 30) String lastName,
        @Valid AddressPatchDto address,
        @Email @Size (max = 100) String email,
        //Ser till så att rätt format används för telefonnummer, ev. plus i början, får innehålla siffror, blanksteg, bindestreck och parenteser
        @Pattern(regexp = "^\\+?[0-9\\s-()]+$") @Size(max = 20) String phoneNumber,
        @Past LocalDate dateOfBirth
) {
}
