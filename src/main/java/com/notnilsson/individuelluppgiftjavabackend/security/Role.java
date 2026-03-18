package com.notnilsson.individuelluppgiftjavabackend.security;

/**
 * <h2>Role</h2>
 * <p>Enumeration som definierar de olika auktoriseringsrollerna i systemet.</p>
 *
 * <p>Tillgängliga roller:</p>
 * <ul>
 *     <li><b>USER</b>: Grundläggande roll för medlemmar som ger åtkomst till "Mina sidor".</li>
 *     <li><b>ADMIN</b>: Administrativ roll som ger fullständig åtkomst till medlemsförvaltning.</li>
 * </ul>
 */
public enum Role {

    USER,
    ADMIN

}
