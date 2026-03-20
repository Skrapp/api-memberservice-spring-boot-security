package com.nilsson.wigellApi.service;

import com.nilsson.wigellApi.dto.*;

import java.util.List;

public interface MemberService {
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
    List<MemberDto> listAll();
    List<MemberLimitedDto> listAllLimited();
    MemberDto get(Long id);
    MemberDto update(Long id, MemberUpdateDto dto);
    MemberDto patch(Long id, MemberPatchDto dto);
    MemberDto create(MemberWithAccountCreateDto dto);
    void delete(Long id);
    boolean isOwner(Long id);
}
