package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;
import java.util.Optional;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.DocumentType;
import com.urbanfeet_backend.Model.AuthResponse;
import com.urbanfeet_backend.Model.RegisterRequest;

public interface UserService {

    public List<User> listarUsers();

    public User obtenerUserPorId(Long id);

    public void guardarUser(User User);

    public void actualizarUser(User User);

    public void eliminarUser(Long id);

    public AuthResponse registerCliente(RegisterRequest request);

    public AuthResponse registerAdmin(RegisterRequest request);

}
