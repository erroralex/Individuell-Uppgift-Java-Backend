package com.notnilsson.individuelluppgiftjavabackend.repository;

import com.notnilsson.individuelluppgiftjavabackend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <h2>MemberRepository</h2>
 * <p>No-code repository som ärver från {@code JpaRepository} för hantering av medlemsentiteter.</p>
 * <p>Tillhandahåller CRUD-operationer</p>
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

}
