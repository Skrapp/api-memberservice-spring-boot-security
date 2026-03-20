package com.nilsson.wigellApi.mapper;

import com.nilsson.wigellApi.dto.*;
import com.nilsson.wigellApi.entity.Address;
import com.nilsson.wigellApi.entity.Member;

public final class MemberMapper {
    private MemberMapper(){}

    public static Member fromCreate(MemberWithAccountCreateDto dto){
        return new Member(dto.firstName(), dto.lastName(), dto.email(), dto.phoneNumber(), dto.dateOfBirth());
    }

    //TODO lägg till AppUser
    public static MemberDto toDto(Member m){
        AddressDto addressDto = AddressMapper.toDto(m.getAddress());

        return new MemberDto(
                m.getId(),
                m.getFirstName(),
                m.getLastName(),
                addressDto,
                m.getEmail(),
                m.getPhoneNumber(),
                m.getDateOfBirth()
                //au.getUsername
        );
    }

    public static MemberLimitedDto toLimitedDto(Member m){
        AddressDto addressDto = AddressMapper.toDto(m.getAddress());

        return new MemberLimitedDto(
                m.getFirstName(),
                m.getLastName(),
                addressDto,
                m.getEmail(),
                m.getPhoneNumber()
        );
    }

    public static void applyUpdate(Member m, MemberUpdateDto dto){
        m.setFirstName(dto.firstName());
        m.setLastName(dto.lastName());
        m.setEmail(dto.email());
        m.setPhoneNumber(dto.phoneNumber());
        m.setDateOfBirth(dto.dateOfBirth());
    }

    public static void applyPatch(Member m, MemberPatchDto dto){
        if(dto.firstName()!= null)
            m.setFirstName(dto.firstName());
        if(dto.lastName()!= null)
            m.setLastName(dto.lastName());
        if(dto.email()!= null)
            m.setEmail(dto.email());
        if(dto.phoneNumber()!= null)
            m.setPhoneNumber(dto.phoneNumber());
        if(dto.dateOfBirth()!= null)
            m.setDateOfBirth(dto.dateOfBirth());
    }
}
