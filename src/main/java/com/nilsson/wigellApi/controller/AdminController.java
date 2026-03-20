package com.nilsson.wigellApi.controller;

import com.nilsson.wigellApi.dto.MemberDto;
import com.nilsson.wigellApi.dto.MemberPatchDto;
import com.nilsson.wigellApi.dto.MemberUpdateDto;
import com.nilsson.wigellApi.dto.MemberWithAccountCreateDto;
import com.nilsson.wigellApi.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/admin/members")
public class AdminController {
    private final MemberService memberService;

    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*
        Admin ska kunna utföra följande aktiviteter
        *Lista medlemmar GET ”/admin/members” – All data på respektive medlem ska hämtas och visas
        *Hämta enskild medlem GET ”/admin/members/{id}” – All data på vald medlem ska hämtas och visas
        *Uppdatera uppgifter PUT ”/admin/members/{id}” – Samtlig data för vald medlem ska uppdateras
        *Uppdatera uppgifter PATCH ”/admin/members/{id}” – Viss data för vald medlem ska uppdateras
        *Lägga till medlem POST ”/admin/members” – Ny medlem ska läggas till i databasen
        *Ta bort medlem DELETE ”/admin/members/{id}” – Angiven medlem ska raderas från databasen*/
    @GetMapping
    public List<MemberDto> listAll(){
        return memberService.listAll();
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

    @PatchMapping("/{id}")
    public ResponseEntity<MemberDto> patch(@PathVariable Long id, @RequestBody @Valid MemberPatchDto dto){
        MemberDto saved = memberService.patch(id, dto);
        return ResponseEntity.ok(saved);
    }

    @PostMapping
    public ResponseEntity<MemberDto> create(@RequestBody @Valid MemberWithAccountCreateDto dto){
        MemberDto saved = memberService.create(dto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
