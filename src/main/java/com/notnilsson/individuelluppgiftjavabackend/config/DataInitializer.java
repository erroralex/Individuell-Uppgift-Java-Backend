package com.notnilsson.individuelluppgiftjavabackend.config;

import com.notnilsson.individuelluppgiftjavabackend.entity.Address;
import com.notnilsson.individuelluppgiftjavabackend.entity.Member;
import com.notnilsson.individuelluppgiftjavabackend.repository.AddressRepository;
import com.notnilsson.individuelluppgiftjavabackend.repository.AppUserRepository;
import com.notnilsson.individuelluppgiftjavabackend.repository.MemberRepository;
import com.notnilsson.individuelluppgiftjavabackend.security.AppUser;
import com.notnilsson.individuelluppgiftjavabackend.security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * <h2>DataInitializer</h2>
 * <p>Denna konfigurationsklass ansvarar för att populera databasen med
 * initial testdata när applikationen startar första gången.</p>
 *
 * <p>Tjänsten ansvarar för:</p>
 * <ul>
 *     <li>Initialisering av fem medlemmar och deras adresser enligt uppgiftsbeskrivningen.</li>
 *     <li>Skapande av standardanvändare för säkerhetslagret (Admin och Medlem).</li>
 *     <li>Länkning av en {@code AppUser} till en befintlig {@code Member}-entitet.</li>
 *     <li>Säkerställande av att data endast läggs till om tabellerna är tomma.</li>
 * </ul>
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(AppUserRepository appUserRepo, PasswordEncoder passwordEncoder, MemberRepository memberRepository, AddressRepository addressRepository) {
        return args -> {

            // Skapa upp Adresser och Medlemmar vid uppstart:
            if (memberRepository.count() == 0) {
                Address addr1 = addressRepository.save(new Address("Storgatan 1",   "Stockholm",    "111 22"));
                Address addr2 = addressRepository.save(new Address("Lilla vägen 5", "Göteborg",     "412 50"));
                Address addr3 = addressRepository.save(new Address("Innertavle 494","Umeå",         "911 20"));
                Address addr4 = addressRepository.save(new Address("Skogsvägen 3",  "Uppsala",      "753 10"));

                Member m1 = new Member("Silvia",    "Bernadotte",   addr1, "queen@royalmail.se",        "0701112233",   LocalDate.of(1960, 5,  20));
                Member m2 = new Member("Tomas",     "Wigell",       addr2, "tomas@javateachers.com",    null,           LocalDate.of(1982, 8,  15));
                Member m3 = new Member("Alexander", "Nilsson",      addr3, "alx@gmail.com",             "0704445566",   LocalDate.of(1978, 12, 10));
                Member m4 = new Member("Robert",    "Gustavsson",   addr4, "bitter@yahoo.com",          "0707778899",   LocalDate.of(1965, 3,  25));
                Member m5 = new Member("Calle",     "Bernadotte",   addr1, "knugen@royalmail.se",       "0709990011",   LocalDate.of(1954, 1,  30));

                memberRepository.saveAll(List.of(m1, m2, m3, m4, m5));
                System.out.println("Skapade 5 medlemmar.");
            }

            // Skapa upp användar-roller (admin och member)
            if (appUserRepo.count() == 0) {
                AppUser admin1 = new AppUser(
                        "admin",
                        passwordEncoder.encode("admin"),
                        Set.of(Role.ADMIN)
                );

                Member memberForUser = memberRepository.findByEmail("tomas@javateachers.com")
                        .orElseThrow(() ->
                                new RuntimeException("Medlem för 'member' användare hittades inte under initialisering."));

                AppUser memberUser = new AppUser(
                        "member",
                        passwordEncoder.encode("member"),
                        Set.of(Role.USER),
                        memberForUser
                );

                appUserRepo.saveAll(List.of(admin1, memberUser));
                System.out.println("Skapat adminkonto 'admin'. Password samma som username");
                System.out.println("Skapat medlemskonto 'member' (linked to Tomas Wigell). Password samma som username");
            }
        };
    }
}
