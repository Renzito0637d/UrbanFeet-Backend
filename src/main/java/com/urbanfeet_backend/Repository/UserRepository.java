package com.urbanfeet_backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.DocumentType;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Método personalizado que busca un usuario en la base de datos por su correo
    // electrónico.
    // Devuelve un Optional<User> para manejar el caso en el que no se encuentre un
    // usuario con ese correo.
    Optional<User> findUserByEmail(String email);

    Optional<User> findByDocumentTypeAndDocumentNumber(DocumentType type, String number);

}
