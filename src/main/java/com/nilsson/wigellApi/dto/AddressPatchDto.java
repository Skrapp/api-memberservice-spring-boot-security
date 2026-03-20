package com.nilsson.wigellApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressPatchDto(
        @Size(max = 25) String street,
        @Size(max = 6) String postalCode,
        @Size(max = 25) String city
) {
}
