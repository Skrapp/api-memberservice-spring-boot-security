package com.nilsson.wigellApi.service;

import com.nilsson.wigellApi.dto.*;

import com.nilsson.wigellApi.entity.Address;
import com.nilsson.wigellApi.entity.Member;
import com.nilsson.wigellApi.exception.MemberNotFoundException;
import com.nilsson.wigellApi.mapper.AddressMapper;
import com.nilsson.wigellApi.mapper.MemberMapper;
import com.nilsson.wigellApi.repository.AddressRepo;
import com.nilsson.wigellApi.repository.AppUserRepo;
import com.nilsson.wigellApi.repository.MemberRepo;
import com.nilsson.wigellApi.security.AppUser;
import com.nilsson.wigellApi.security.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepo memberRepo;
    private final AddressRepo addressRepo;
    private final AppUserRepo appUserRepo;
    private final PasswordEncoder encoder;

    public MemberServiceImpl(MemberRepo memberRepo, AddressRepo addressRepo, AppUserRepo appUserRepo, PasswordEncoder encoder) {
        this.memberRepo = memberRepo;
        this.addressRepo = addressRepo;
        this.appUserRepo = appUserRepo;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDto> listAll(){
        return memberRepo.findAll().stream().map(MemberMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberLimitedDto> listAllLimited() {
        return memberRepo.findAll().stream().map(MemberMapper::toLimitedDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or @memberServiceImpl.isOwner(#id)")
    public MemberDto get(Long id) {
        Member member = memberRepo.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        return MemberMapper.toDto(member);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @memberServiceImpl.isOwner(#id)")
    public MemberDto update(Long id, MemberUpdateDto dto) {
        //Checka så födelsedatum inte används
        if(memberRepo.existsByDateOfBirth(dto.dateOfBirth()))
            throw new IllegalArgumentException("Födelsedatum används redan.");

        Member member =  memberRepo.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        MemberMapper.applyUpdate(member, dto);

        //se ifall address redan finns
        Address address = addressRepo
                .findAddressByStreetAndPostalCodeAndCity(
                        dto.address().street(),
                        dto.address().postalCode(),
                        dto.address().city())
                .orElseGet(() ->addressRepo.save(new Address(
                        dto.address().street(),
                        dto.address().postalCode(),
                        dto.address().city()))
                );
        member.setAddress(address);

        Member saved = memberRepo.save(member);
        return MemberMapper.toDto(saved);
    }

    @Override
    @Transactional
    public MemberDto patch(Long id, MemberPatchDto dto) {
        //Checka så födelsedatum inte används
        if(memberRepo.existsByDateOfBirth(dto.dateOfBirth()))
            throw new IllegalArgumentException("Födelsedatum används redan.");

        Member member =  memberRepo.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        MemberMapper.applyPatch(member, dto);
        if(dto.address() != null) {
            Address currentAddress = new Address(member.getAddress().getStreet(), member.getAddress().getPostalCode(), member.getAddress().getCity());
            AddressMapper.applyPatch(currentAddress, dto.address());

            //se ifall address redan finns
            Address address = addressRepo.findAddressByStreetAndPostalCodeAndCity(
                            currentAddress.getStreet(),
                            currentAddress.getPostalCode(),
                            currentAddress.getCity())
                    .orElseGet(() -> addressRepo.save(new Address(
                            currentAddress.getStreet(),
                            currentAddress.getPostalCode(),
                            currentAddress.getCity()))
                    );
            member.setAddress(address);
        }

        Member saved = memberRepo.save(member);
        return MemberMapper.toDto(saved);
    }

    @Override
    @Transactional
    public MemberDto create(MemberWithAccountCreateDto dto) {
        //Checka så att användarnamn och födelsedatum inte används
        if(memberRepo.existsByDateOfBirth(dto.dateOfBirth()))
            throw new IllegalArgumentException("Födelsedatum används redan.");
        if(appUserRepo.existsByUsername(dto.username()))
            throw new IllegalArgumentException("Användarnamn används redan.");

        Member member = MemberMapper.fromCreate(dto);

        //se ifall address redan finns
        Address address = addressRepo
                .findAddressByStreetAndPostalCodeAndCity(
                        dto.address().street(),
                        dto.address().postalCode(),
                        dto.address().city())
                .orElseGet(() -> addressRepo.save(new Address(
                        dto.address().street(),
                        dto.address().postalCode(),
                        dto.address().city()))
                );
        member.setAddress(address);

        member = memberRepo.save(member);

        //Skapa användare
        AppUser appUser = new AppUser(
                dto.username(),
                encoder.encode(dto.password()),
                Set.of(Role.USER),
                member
        );
        appUser = appUserRepo.save(appUser);
        member.setAppUser(appUser);

        return MemberMapper.toDto(member);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!memberRepo.existsById(id))
            throw new MemberNotFoundException(id);
        memberRepo.deleteById(id);
    }

    @Override
    public boolean isOwner(Long id) {
        //Se om inloggad användare äger medlem
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        AppUser owner = appUserRepo.findByMemberId(id)
                .orElseThrow(() -> new AccessDeniedException("Medlem är ej kopplad till någon användare."));

        if(!currentUser.equals(owner.getUsername())){
            throw new AccessDeniedException("Du äger ej denna medlem.");
        }
        return true;
    }
}
