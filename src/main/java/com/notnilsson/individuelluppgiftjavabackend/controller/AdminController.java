package com.notnilsson.individuelluppgiftjavabackend.controller;

import com.notnilsson.individuelluppgiftjavabackend.dto.MemberAdminDTO;
import com.notnilsson.individuelluppgiftjavabackend.dto.MemberRequestDTO;
import com.notnilsson.individuelluppgiftjavabackend.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h2>AdminController</h2>
 * <p>Denna kontroller hanterar administrativa operationer för medlemmar i systemet.
 * Den tillhandahåller endpoints för fullständig resurshantering (CRUD) av medlemsdata,
 * inklusive listning, hämtning, skapande, fullständig uppdatering, partiell uppdatering
 * och borttagning av medlemmar.</p>
 *
 * <p>Aktiviteter som stöds:</p>
 * <ul>
 *     <li><b>GET /admin/members</b>: Hämtar all data för samtliga medlemmar.</li>
 *     <li><b>GET /admin/members/{id}</b>: Hämtar all data för en specifik medlem baserat på ID.</li>
 *     <li><b>PUT /admin/members/{id}</b>: Uppdaterar all data för vald medlem.</li>
 *     <li><b>PATCH /admin/members/{id}</b>: Uppdaterar specifik data för vald medlem.</li>
 *     <li><b>POST /admin/members</b>: Lägger till en ny medlem i databasen.</li>
 *     <li><b>DELETE /admin/members/{id}</b>: Raderar angiven medlem från databasen.</li>
 * </ul>
 *
 * @param adminService Tjänst som innehåller affärslogik för administrativa medlemsärenden.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberAdminDTO>> getAllMembers() {
        return ResponseEntity.ok(adminService.getAllMembers());
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberAdminDTO> findMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.findMemberById(id));
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberAdminDTO> updateMember(@PathVariable Long id, @RequestBody MemberRequestDTO memberRequestDTO) {
        return ResponseEntity.ok(adminService.updateMember(id, memberRequestDTO));
    }

    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberAdminDTO> patchMember(@PathVariable Long id, @RequestBody MemberRequestDTO memberRequestDTO) {
        return ResponseEntity.ok(adminService.patchMember(id, memberRequestDTO));
    }

    @PostMapping("/members")
    public ResponseEntity<MemberAdminDTO> addMember(@RequestBody MemberRequestDTO memberRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.addMember(memberRequestDTO));
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        adminService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}