package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.Config.Auth.JwtService;
import com.urbanfeet_backend.DAO.Interfaces.UserDAO;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.RoleName;
import com.urbanfeet_backend.Model.AuthResponse;
import com.urbanfeet_backend.Model.RegisterRequest;
import com.urbanfeet_backend.Services.Interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private void validateUniqueness(RegisterRequest r) {
        userDAO.buscarUserPorCorreo(r.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Email ya registrado");
                });

        userDAO.buscarPorNumeroDocumento(
                r.getDocumentType(),
                r.getDocumentNumber()).ifPresent(u -> {
                    throw new IllegalArgumentException("Documento ya registrado");
                });
    }

    @Override
    public List<User> listarUsers() {
        return userDAO.listarUsers();
    }

    @Override
    public User obtenerUserPorId(Integer id) {
        return userDAO.obtenerUserPorId(id);
    }

    @Override
    public void guardarUser(User User) {
        userDAO.guardarUser(User);
    }

    @Override
    public void actualizarUser(User User) {
        userDAO.actualizarUser(User);
    }

    @Override
    public void eliminarUser(Integer id) {
        userDAO.eliminarUser(id);
    }

    @Override
    public AuthResponse registerCliente(RegisterRequest request) {
        validateUniqueness(request);
        var encodedPassword = passwordEncoder.encode(request.getPassword());

        var user = User.from(request, encodedPassword, RoleName.CLIENTE);
        // Guardar el nuevo usuario en la base de datos
        userDAO.guardarUser(user);
        // Generar un token JWT para el usuario registrado
        var jwtToken = jwtService.generateAccessToken(user);
        // Devolver el token generado dentro de un objeto AuthResponse
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse registerAdmin(RegisterRequest request) {
        validateUniqueness(request);
        var encodedPassword = passwordEncoder.encode(request.getPassword());

        var user = User.from(request, encodedPassword, RoleName.ADMIN);
        // Guardar el nuevo usuario en la base de datos
        userDAO.guardarUser(user);
        // Generar un token JWT para el usuario registrado
        var jwtToken = jwtService.generateAccessToken(user);
        // Devolver el token generado dentro de un objeto AuthResponse
        return AuthResponse.builder().token(jwtToken).build();
    }

}
