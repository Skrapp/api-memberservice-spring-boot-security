package com.nilsson.wigellApi.mapper;

import com.nilsson.wigellApi.dto.AddressCreateDto;
import com.nilsson.wigellApi.dto.AddressDto;
import com.nilsson.wigellApi.dto.AddressPatchDto;
import com.nilsson.wigellApi.entity.Address;

public final class AddressMapper {
    private AddressMapper() {
    }

    public static AddressDto toDto(Address a){
        return new AddressDto(a.getStreet(), a.getPostalCode(), a.getCity());
    }

    public static void applyPatch(Address a, AddressPatchDto dto){
        if(dto.street() != null)
            a.setStreet(dto.street());
        if (dto.postalCode() != null)
            a.setPostalCode(dto.postalCode());
        if(dto.city() != null)
            a.setCity(dto.city());
    }
}
