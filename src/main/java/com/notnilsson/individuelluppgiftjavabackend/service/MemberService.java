package com.notnilsson.individuelluppgiftjavabackend.service;

import com.notnilsson.individuelluppgiftjavabackend.dto.AddressDTO;
import com.notnilsson.individuelluppgiftjavabackend.dto.MemberPublicDTO;
import com.notnilsson.individuelluppgiftjavabackend.dto.MemberRequestDTO;
import com.notnilsson.individuelluppgiftjavabackend.entity.Address;
import com.notnilsson.individuelluppgiftjavabackend.entity.Member;
import com.notnilsson.individuelluppgiftjavabackend.exception.MemberNotFoundException;
import com.notnilsson.individuelluppgiftjavabackend.exception.ResourceConflictException;
import com.notnilsson.individuelluppgiftjavabackend.repository.AddressRepository;
import com.notnilsson.individuelluppgiftjavabackend.repository.AppUserRepository;
import com.notnilsson.individuelluppgiftjavabackend.repository.MemberRepository;
import com.notnilsson.individuelluppgiftjavabackend.security.AppUser;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h2>MemberService</h2>
 * <p>Denna serviceklass hanterar affärslogik relaterad till vanliga medlemmar.
 * Den tillhandahåller funktioner för att visa en publik profilvy av samtliga medlemmar
 * och hanterar säker uppdatering av den inloggade medlemmens egna uppgifter.</p>
 *
 * <p>Servicen ansvarar för:</p>
 * <ul>
 *     <li>Att mappa medlemsdata till en publik vy (DTO) för visning i medlemslistan.</li>
 *     <li>Validering av den inloggade användarens identitet mot den profil som ska uppdateras.</li>
 *     <li>Hantering av adressupplösning vid uppdatering av medlemsuppgifter.</li>
 *     <li>Säkerställande av dataintegritet vid ändring av unika fält som födelsedatum.</li>
 * </ul>
 *
 * @param memberRepository Repository för hantering av medlemsentiteter.
 * @param addressRepository Repository för hantering av adressentiteter.
 * @param appUserRepository Repository för hantering av applikationsanvändare (inloggning).
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final AppUserRepository appUserRepository;

    public MemberService(MemberRepository memberRepository,
                         AddressRepository addressRepository,
                         AppUserRepository appUserRepository) {

        this.memberRepository = memberRepository;
        this.addressRepository = addressRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<MemberPublicDTO> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::toPublicDTO)
                .toList();
    }

    public MemberPublicDTO updateMember(Long id, MemberRequestDTO dto, String username) {
        AppUser currentUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("Hittade inte autentiserad användare."));

        if (currentUser.getMember() == null || !currentUser.getMember().getId().equals(id)) {
            throw new AccessDeniedException("Du är inte auktoriserad att uppdatera denna medlems information.");
        }

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
            return toPublicDTO(memberRepository.save(member));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException("Kan inte uppdatera medlem: födelsedatum " + dto.dateOfBirth() + " existerar redan för en annan medlem.");
        }
    }

    private Address resolveAddress(MemberRequestDTO dto) {
        AddressDTO addressDTO = dto.address();
        return addressRepository.findByStreetAndCityAndPostalCode(
                        addressDTO.street(), addressDTO.city(), addressDTO.postalCode())
                .orElseGet(() -> addressRepository.save(
                        new Address(addressDTO.street(), addressDTO.city(), addressDTO.postalCode())));
    }

    private MemberPublicDTO toPublicDTO(Member member) {
        Address a = member.getAddress();
        return new MemberPublicDTO(
                member.getFirstName(),
                member.getLastName(),
                new AddressDTO(a.getStreet(), a.getCity(), a.getPostalCode()),
                member.getEmail(),
                member.getPhone()
        );
    }
}
