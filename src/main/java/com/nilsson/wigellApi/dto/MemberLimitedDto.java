package com.nilsson.wigellApi.dto;

public record MemberLimitedDto(
        String firstName,
        String lastName,
        AddressDto address,
        String email,
        String phoneNumber
) {
}
