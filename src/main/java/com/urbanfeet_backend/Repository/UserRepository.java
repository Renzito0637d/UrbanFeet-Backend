package com.urbanfeet_backend.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.DocumentType;
import com.urbanfeet_backend.Entity.Enum.RoleName;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findByDocumentTypeAndDocumentNumber(DocumentType type, String number);

    Page<User> findByRoles(RoleName role, Pageable pageable);

    @Query("""
        SELECT u FROM User u 
        WHERE NOT EXISTS (
            SELECT r FROM User u2 JOIN u2.roles r
            WHERE u2 = u AND r = :role
        )
    """)
    Page<User> findUsersWithoutRole(@Param("role") RoleName role, Pageable pageable);
}
