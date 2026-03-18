package com.notnilsson.individuelluppgiftjavabackend.controller;

import com.notnilsson.individuelluppgiftjavabackend.dto.MemberPublicDTO;
import com.notnilsson.individuelluppgiftjavabackend.dto.MemberRequestDTO;
import com.notnilsson.individuelluppgiftjavabackend.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h2>MemberController</h2>
 * <p>Denna kontroller hanterar åtgärder som är specifika för medlemmar på "Mina sidor".
 * Kontrollern exponerar funktionalitet för att visa en begränsad vy av medlemslistan
 * samt uppdatering av den egna medlemsinformationen för inloggade användare.</p>
 *
 * <p>Aktiviteter som stöds:</p>
 * <ul>
 *     <li><b>GET /mypages/members</b>: Hämtar en publik vy (förnamn, efternamn, adress, e-post, telefon) av samtliga medlemmar.</li>
 *     <li><b>PUT /mypages/members/{id}</b>: Uppdaterar informationen för den inloggade medlemmen.</li>
 * </ul>
 *
 * @param memberService
 *         Tjänst som hanterar medlemsrelaterad logik och åtkomstkontroll för användare.
 */
@RestController
@RequestMapping("/mypages")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberPublicDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberPublicDTO> updateMember(@PathVariable Long id,
                                                        @RequestBody MemberRequestDTO memberRequestDTO,
                                                        Authentication authentication) {

        return ResponseEntity.ok(memberService.updateMember(id, memberRequestDTO, authentication.getName()));
    }
}
