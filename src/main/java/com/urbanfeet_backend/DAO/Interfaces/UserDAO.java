package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;
import java.util.Optional;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.DocumentType;

public interface UserDAO {
    public List<User> listarUsers();

    public User obtenerUserPorId(Long id);

    public void guardarUser(User User);

    public void actualizarUser(User User);

    public void eliminarUser(Long id);

    public Optional<User> buscarUserPorCorreo(String email);
    
    public Optional<User> buscarPorNumeroDocumento(DocumentType documentType, String documentNumber);
}
