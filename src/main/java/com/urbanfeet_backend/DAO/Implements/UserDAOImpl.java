package com.urbanfeet_backend.DAO.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.UserDAO;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.DocumentType;
import com.urbanfeet_backend.Entity.Enum.RoleName;
import com.urbanfeet_backend.Model.UserDTOs.UserResponse;
import com.urbanfeet_backend.Repository.UserRepository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listarUsers() {
        return userRepository.findAll();
    }

    @Override
    public User obtenerUserPorId(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void guardarUser(User User) {
        userRepository.save(User);
    }

    @Override
    public User actualizarUser(User User) {
        return userRepository.save(User);
    }

    @Override
    public void eliminarUser(Integer id) {
        User usuarioObj = userRepository.findById(id).get();
        userRepository.delete(usuarioObj);
    }

    @Override
    public Optional<User> buscarUserPorCorreo(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> buscarPorNumeroDocumento(DocumentType documentType, String documentNumber) {
        return userRepository.findByDocumentTypeAndDocumentNumber(documentType, documentNumber);
    }

    @Override
    public Page<UserResponse> listarPorRol(RoleName role, Pageable pageable) {
        // 1. Obtenemos la página de Entidades
        Page<User> usersPage = userRepository.findByRoles(role, pageable);
        // 2. Convertimos cada Entidad a DTO usando el método map() y nuestra función
        // fromEntity
        return usersPage.map(UserResponse::fromEntity);
    }

    @Override
    public Page<UserResponse> listarNoClientes(Pageable pageable) {
        // 1. Obtenemos la página de Entidades
        Page<User> usersPage = userRepository.findByRolesNotMember(RoleName.CLIENTE, pageable);
        // 2. Convertimos a DTO
        return usersPage.map(UserResponse::fromEntity);
    }

}
