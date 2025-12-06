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

    // Método personalizado que busca un usuario en la base de datos por su correo
    // electrónico.
    // Devuelve un Optional<User> para manejar el caso en el que no se encuentre un
    // usuario con ese correo.
    Optional<User> findUserByEmail(String email);

    Optional<User> findByDocumentTypeAndDocumentNumber(DocumentType type, String number);

    Page<User> findByRoles(RoleName role, Pageable pageable);

    @Query("SELECT u FROM User u WHERE :role NOT MEMBER OF u.roles")
    Page<User> findByRolesNotMember(@Param("role") RoleName role, Pageable pageable);

}
