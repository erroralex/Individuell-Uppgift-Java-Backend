package com.notnilsson.individuelluppgiftjavabackend.service;

import com.notnilsson.individuelluppgiftjavabackend.dto.AddressDTO;
import com.notnilsson.individuelluppgiftjavabackend.dto.MemberAdminDTO;
import com.notnilsson.individuelluppgiftjavabackend.dto.MemberRequestDTO;
import com.notnilsson.individuelluppgiftjavabackend.entity.Address;
import com.notnilsson.individuelluppgiftjavabackend.entity.Member;
import com.notnilsson.individuelluppgiftjavabackend.exception.MemberNotFoundException;
import com.notnilsson.individuelluppgiftjavabackend.exception.ResourceConflictException;
import com.notnilsson.individuelluppgiftjavabackend.repository.AddressRepository;
import com.notnilsson.individuelluppgiftjavabackend.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h2>AdminService</h2>
 * <p>Denna serviceklass hanterar den administrativa affärslogiken för medlemmar.
 * Den fungerar som ett lager mellan {@code AdminController} och databaslagret
 * (repositories) för att utföra operationer som kräver administratörsbehörighet.</p>
 *
 * <p>Servicen ansvarar för:</p>
 * <ul>
 *     <li>Hämtning av fullständig medlemsinformation för alla medlemmar.</li>
 *     <li>Hantering av specifika medlemmar via ID.</li>
 *     <li>Validering och lagring av nya medlemmar.</li>
 *     <li>Fullständiga och partiella uppdateringar av medlemsdata.</li>
 *     <li>Borttagning av medlemmar samt hantering av unika begränsningar (t.ex. födelsedatum).</li>
 * </ul>
 *
 * @param memberRepository Repository för hantering av medlemsentiteter.
 * @param addressRepository Repository för hantering av adressentiteter.
 */
@Service
public class AdminService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;

    public AdminService(MemberRepository memberRepository, AddressRepository addressRepository) {
        this.memberRepository = memberRepository;
        this.addressRepository = addressRepository;
    }

    public List<MemberAdminDTO> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::toAdminDTO)
                .toList();
    }

    public MemberAdminDTO findMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Medlem med ID " + id + " hittades inte"));
        return toAdminDTO(member);
    }

    public MemberAdminDTO addMember(MemberRequestDTO dto) {
        Address address = resolveAddress(dto);
        Member member = new Member(
                dto.firstName(),
                dto.lastName(),
                address,
                dto.email(),
                dto.phone(),
                dto.dateOfBirth()
        );
        try {
            return toAdminDTO(memberRepository.save(member));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException("Medlem med födelsedatum " + dto.dateOfBirth() + " existerar redan.");
        }
    }

    public MemberAdminDTO updateMember(Long id, MemberRequestDTO dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Medlem med ID " + id + " hittades inte"));

        Address address = resolveAddress(dto);
        member.setFirstName(dto.firstName());
        member.setLastName(dto.lastName());
        member.setAddress(address);
        member.setEmail(dto.email());
        member.setPhone(dto.phone());
        member.setDateOfBirth(dto.dateOfBirth());

        try {
            return toAdminDTO(memberRepository.save(member));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException("Kan inte uppdatera medlem: födelsedatum " + dto.dateOfBirth() + " existerar redan för en annan medlem.");
        }
    }

    public MemberAdminDTO patchMember(Long id, MemberRequestDTO dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Medlem med ID " + id + " hittades inte"));

        if (dto.firstName() != null) member.setFirstName(dto.firstName());
        if (dto.lastName() != null) member.setLastName(dto.lastName());
        if (dto.address() != null) member.setAddress(resolveAddress(dto));
        if (dto.email() != null) member.setEmail(dto.email());
        if (dto.phone() != null) member.setPhone(dto.phone());
        if (dto.dateOfBirth() != null) member.setDateOfBirth(dto.dateOfBirth());

        try {
            return toAdminDTO(memberRepository.save(member));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException("Kan inte patcha medlem: födelsedatum " + dto.dateOfBirth() + " existerar redan för en annan medlem.");
        }
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Medlem med ID " + id + " hittades inte"));
        memberRepository.delete(member);
    }

    private Address resolveAddress(MemberRequestDTO dto) {
        AddressDTO addressDTO = dto.address();
        return addressRepository.findByStreetAndCityAndPostalCode(
                        addressDTO.street(), addressDTO.city(), addressDTO.postalCode())
                .orElseGet(() -> addressRepository.save(
                        new Address(addressDTO.street(), addressDTO.city(), addressDTO.postalCode())));
    }

    private MemberAdminDTO toAdminDTO(Member member) {
        Address a = member.getAddress();
        return new MemberAdminDTO(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                new AddressDTO(a.getStreet(), a.getCity(), a.getPostalCode()),
                member.getEmail(),
                member.getPhone(),
                member.getDateOfBirth()
        );
    }
}
