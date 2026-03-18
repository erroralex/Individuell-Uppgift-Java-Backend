package com.notnilsson.individuelluppgiftjavabackend.security;

import com.notnilsson.individuelluppgiftjavabackend.exception.MemberNotFoundException;
import com.notnilsson.individuelluppgiftjavabackend.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * <h2>AppUserDetailsService</h2>
 * <p>Denna service implementerar {@code UserDetailsService} för att möjliggöra
 * anpassad användarautentisering via databasen.</p>
 *
 * <p>Servicen ansvarar för:</p>
 * <ul>
 *     <li>Hämtning av användarinformation baserat på användarnamn.</li>
 *     <li>Integration med {@code AppUserRepository} för att slå upp {@code AppUser}-entiteter.</li>
 *     <li>Hantering av felscenarier när en användare inte kan hittas i systemet.</li>
 *     <li>Tillhandahållande av auktoriseringsuppgifter (Authorities) till Spring Security.</li>
 * </ul>
 *
 * @param {@code appUserRepository} Repository för åtkomst av användardata i databasen.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws MemberNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("Medlem ej hittad"));

        var authorities = appUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(authorities)
                .build();
    }
}
