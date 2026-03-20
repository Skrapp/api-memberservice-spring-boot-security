package com.nilsson.wigellApi.mapper;

import com.nilsson.wigellApi.dto.AddressCreateDto;
import com.nilsson.wigellApi.dto.AddressDto;
import com.nilsson.wigellApi.entity.Address;

public final class AddressMapper {
    private AddressMapper() {
    }

    public static Address fromCreate(AddressCreateDto dto){
        return new Address(dto.street(), dto.postalCode(), dto.city());
    }

    public static AddressDto toDto(Address a){
        return new AddressDto(a.getStreet(), a.getPostalCode(), a.getCity());
    }

    public static void applyUpdate(Address a, AddressCreateDto dto){
        a.setStreet(dto.street());
        a.setPostalCode(dto.postalCode());
        a.setCity(dto.city());
    }

    public static void applyPatch(Address a, AddressCreateDto dto){
        if(dto.street() != null)
            a.setStreet(dto.street());
        if (dto.postalCode() != null)
            a.setPostalCode(dto.postalCode());
        if(dto.city() != null)
            a.setCity(dto.city());
    }
}
