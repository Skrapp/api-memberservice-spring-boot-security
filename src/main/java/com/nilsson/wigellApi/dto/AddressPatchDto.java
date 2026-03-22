package com.nilsson.wigellApi.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressPatchDto(
        @Size(max = 25) String street,
        //postnummer får endast innehålla siffror och mellanslag
        @Pattern (regexp = "[0-9\\s]+$") @Size(max = 6) String postalCode,
        @Size(max = 25) String city
) {
}
