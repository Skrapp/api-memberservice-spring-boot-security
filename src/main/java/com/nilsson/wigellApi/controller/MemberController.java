package com.nilsson.wigellApi.controller;

import com.nilsson.wigellApi.dto.MemberDto;
import com.nilsson.wigellApi.dto.MemberLimitedDto;
import com.nilsson.wigellApi.dto.MemberUpdateDto;
import com.nilsson.wigellApi.service.MemberServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypages/members")
public class MemberController {
    /*
    Medlemmar ska kunna utföra följande aktiviteter
    *Lista medlemmar GET ”/mypages/members” – firstName, lastName, addressCreateDto, email och phone på
    samtliga medlemmar ska hämtas och visas
    *Uppdatera uppgifter PUT ”/mypages/members/{id}” – Data för den inloggade medlemmen ska
    uppdateras*/

    private final MemberServiceImpl memberService;

    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberService = memberServiceImpl;
    }

    @GetMapping
    public List<MemberLimitedDto> listAll(){
        return memberService.listAllLimited();
    }

    @GetMapping("/{id}")
    public MemberDto get(@PathVariable Long id){
        return memberService.get(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> update(@PathVariable Long id, @RequestBody @Valid MemberUpdateDto dto){
        MemberDto saved = memberService.update(id, dto);
        return ResponseEntity.ok(saved);
    }
}
