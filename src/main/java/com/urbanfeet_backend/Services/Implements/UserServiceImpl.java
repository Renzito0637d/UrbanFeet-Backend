package com.urbanfeet_backend.Services.Implements;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.Config.JwtService;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.RoleName;
import com.urbanfeet_backend.Model.AuthResponse;
import com.urbanfeet_backend.Model.RegisterRequest;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Services.Interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private void validateUniqueness(RegisterRequest r) {
        userRepository.findUserByEmail(r.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Email ya registrado");
                });

        userRepository.findByDocumentTypeAndDocumentNumber(
                r.getDocumentType(),
                r.getDocumentNumber()).ifPresent(u -> {
                    throw new IllegalArgumentException("Documento ya registrado");
                });
    }

    @Override
    public AuthResponse registerCliente(RegisterRequest request) {
        validateUniqueness(request);
        var encodedPassword = passwordEncoder.encode(request.getPassword());

        var user = User.from(request, encodedPassword, RoleName.CLIENTE);
        // Guardar el nuevo usuario en la base de datos
        userRepository.save(user);
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
        userRepository.save(user);
        // Generar un token JWT para el usuario registrado
        var jwtToken = jwtService.generateAccessToken(user);
        // Devolver el token generado dentro de un objeto AuthResponse
        return AuthResponse.builder().token(jwtToken).build();
    }

}
