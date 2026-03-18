package com.notnilsson.individuelluppgiftjavabackend.repository;

import com.notnilsson.individuelluppgiftjavabackend.security.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <h2>AppUserRepository</h2>
 * <p>No-code repository som ärver från {@code JpaRepository} för hantering av applikationsanvändare-entiteter.</p>
 * <p>Tillhandahåller CRUD-operationer</p>
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByUsername(String username);
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByMemberId(Long memberId);

}
