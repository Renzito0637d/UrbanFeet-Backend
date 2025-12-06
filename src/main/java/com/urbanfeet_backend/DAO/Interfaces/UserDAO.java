package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.DocumentType;
import com.urbanfeet_backend.Entity.Enum.RoleName;
import com.urbanfeet_backend.Model.UserDTOs.UserResponse;

public interface UserDAO {
    public List<User> listarUsers();

    public User obtenerUserPorId(Integer id);

    public void guardarUser(User User);

    public User actualizarUser(User User);

    public void eliminarUser(Integer id);

    public Optional<User> buscarUserPorCorreo(String email);
    
    public Optional<User> buscarPorNumeroDocumento(DocumentType documentType, String documentNumber);

    Page<UserResponse> listarPorRol(RoleName role, Pageable pageable);

    Page<UserResponse> listarNoClientes(Pageable pageable);
    
}
