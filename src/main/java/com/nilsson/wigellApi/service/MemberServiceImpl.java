package com.nilsson.wigellApi.service;

import com.nilsson.wigellApi.dto.*;

import com.nilsson.wigellApi.entity.Address;
import com.nilsson.wigellApi.entity.Member;
import com.nilsson.wigellApi.exception.MemberNotFoundException;
import com.nilsson.wigellApi.mapper.AddressMapper;
import com.nilsson.wigellApi.mapper.MemberMapper;
import com.nilsson.wigellApi.repository.AddressRepo;
import com.nilsson.wigellApi.repository.MemberRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepo memberRepo;
    private final AddressRepo addressRepo;

    public MemberServiceImpl(MemberRepo memberRepo, AddressRepo addressRepo) {
        this.memberRepo = memberRepo;
        this.addressRepo = addressRepo;
    }

    /*
    Admin ska kunna utföra följande aktiviteter
    *Lista medlemmar GET ”/admin/members” – All data på respektive medlem ska hämtas och visas
    *Hämta enskild medlem GET ”/admin/members/{id}” – All data på vald medlem ska hämtas och visas
    *Uppdatera uppgifter PUT ”/admin/members/{id}” – Samtlig data för vald medlem ska uppdateras
    *Uppdatera uppgifter PATCH ”/admin/members/{id}” – Viss data för vald medlem ska uppdateras
    *Lägga till medlem POST ”/admin/members” – Ny medlem ska läggas till i databasen
    *Ta bort medlem DELETE ”/admin/members/{id}” – Angiven medlem ska raderas från databasen

    Medlemmar ska kunna utföra följande aktiviteter
    *Lista medlemmar GET ”/mypages/members” – firstName, lastName, addressCreateDto, email och phone på
    samtliga medlemmar ska hämtas och visas
    *Uppdatera uppgifter PUT ”/mypages/members/{id}” – Data för den inloggade medlemmen ska
    uppdateras*/

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
    public MemberDto get(Long id) {
        //TODO checka om man har behörighet
        Member member =  memberRepo.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        return MemberMapper.toDto(member);
    }

    @Override
    @Transactional
    public MemberDto update(Long id, MemberUpdateDto dto) {
        //TODO checka om man har behörighet
        //TODO checka så att användarnamn och mail inte används

        Member member =  memberRepo.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        MemberMapper.applyUpdate(member, dto);

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
        //TODO checka så att användarnamn och mail inte används

        Member member =  memberRepo.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        MemberMapper.applyPatch(member, dto);
        if(dto.address() != null) {
            Address currentAddress = new Address(member.getAddress().getStreet(), member.getAddress().getPostalCode(), member.getAddress().getCity());
            AddressMapper.applyPatch(currentAddress, dto.address());

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
        //TODO checka så att användarnamn och mail inte används
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

        Member saved = memberRepo.save(member);

        //TODO spara användare
        return MemberMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!memberRepo.existsById(id))
            throw new MemberNotFoundException(id);
        memberRepo.deleteById(id);
    }
}
