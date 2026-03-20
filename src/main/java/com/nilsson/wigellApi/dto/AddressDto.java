package com.nilsson.wigellApi.dto;

public record AddressDto(
        String street,
        String postalCode,
        String city
) {
}
