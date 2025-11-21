package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Model.AuthDTOs.AuthResponse;
import com.urbanfeet_backend.Model.AuthDTOs.RegisterRequest;

public interface UserService {

    public List<User> listarUsers();

    public User obtenerUserPorId(Integer id);

    public void guardarUser(User User);

    public void actualizarUser(User User);

    public void eliminarUser(Integer id);

    public AuthResponse registerCliente(RegisterRequest request);

    public AuthResponse registerAdmin(RegisterRequest request);

}
