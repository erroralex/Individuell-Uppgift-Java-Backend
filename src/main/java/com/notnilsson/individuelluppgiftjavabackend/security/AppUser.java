package com.notnilsson.individuelluppgiftjavabackend.security;

import com.notnilsson.individuelluppgiftjavabackend.entity.Member;
import jakarta.persistence.*;
import java.util.Set;

/**
 * <h2>AppUser</h2>
 * <p>Entitetsklass som representerar en applikationsanvändare för autentisering.
 * Klassen implementerar {@code UserDetails} för att integreras med Spring Security.</p>
 *
 * <p>Huvudsakliga ansvarsområden:</p>
 * <ul>
 *     <li>Lagring av användaruppgifter såsom användarnamn och krypterat lösenord.</li>
 *     <li>Hantering av en uppsättning {@code Role}-behörigheter.</li>
 *     <li>Koppling till en specifik {@code Member}-entitet för personlig medlemsdata.</li>
 *
 * </ul>
 */
@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "app_user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    protected AppUser() {

    }

    public AppUser(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public AppUser(String username, String password, Set<Role> roles, Member member) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
