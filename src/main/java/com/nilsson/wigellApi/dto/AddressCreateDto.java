package com.nilsson.wigellApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressCreateDto(
        @NotBlank @Size(max = 25) String street,
        @NotBlank @Size(max = 6) String postalCode,
        @NotBlank @Size(max = 25) String city
) {
}
