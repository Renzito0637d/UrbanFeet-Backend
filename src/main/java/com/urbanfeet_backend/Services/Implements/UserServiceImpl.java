package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.Config.Auth.JwtService;
import com.urbanfeet_backend.DAO.Interfaces.CarritoDAO;
import com.urbanfeet_backend.DAO.Interfaces.UserDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.RoleName;
import com.urbanfeet_backend.Model.AuthDTOs.AuthResponse;
import com.urbanfeet_backend.Model.AuthDTOs.RegisterRequest;
import com.urbanfeet_backend.Model.UserDTOs.UserResponse;
import com.urbanfeet_backend.Model.UserDTOs.UserUpdateRequest;
import com.urbanfeet_backend.Services.Interfaces.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDAO userDAO;

    @Autowired
    private final CarritoDAO carritoDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserDAO userDAO, CarritoDAO carritoDao, PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userDAO = userDAO;
        this.carritoDao = carritoDao;
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

        Carrito carrito = new Carrito();
        carrito.setUser(user);

        carritoDao.save(carrito);
        // Generar un token JWT para el usuario registrado
        var jwtToken = jwtService.generateAccessToken(user);
        // Devolver el token generado dentro de un objeto AuthResponse
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse registerAdmin(RegisterRequest request) {
        validateUniqueness(request);
        var encodedPassword = passwordEncoder.encode(request.getPassword());

        RoleName roleAssign = (request.getRole() != null) ? request.getRole() : RoleName.ADMIN;

        // Evitar que alguien cree un CLIENTE por este medio (seguridad opcional)
        if (roleAssign == RoleName.CLIENTE) {
            throw new IllegalArgumentException("No se pueden crear clientes desde el panel interno");
        }

        var user = User.from(request, encodedPassword, roleAssign); // Usamos la variable roleAssign
        // Guardar el nuevo usuario en la base de datos
        userDAO.guardarUser(user);
        // Generar un token JWT para el usuario registrado
        var jwtToken = jwtService.generateAccessToken(user);
        // Devolver el token generado dentro de un objeto AuthResponse
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public Page<UserResponse> listarPorRol(RoleName role, Pageable pageable) {
        return userDAO.listarPorRol(role, pageable);
    }

    @Override
    public Page<UserResponse> listarNoClientes(Pageable pageable) {
        return userDAO.listarNoClientes(pageable);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Integer id, UserUpdateRequest request) {
        // 1. Buscar usuario existente
        User user = userDAO.obtenerUserPorId(id);

        // 2. Actualizar campos básicos
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setPhone(request.getPhone());
        user.setDocumentType(request.getDocumentType());
        user.setDocumentNumber(request.getDocumentNumber());

        // 3. Lógica para actualizar ROL (Solo para internos)
        // Verificamos que se envíe un rol Y que el usuario actual NO sea un cliente.
        if (request.getRole() != null && !user.getRoles().contains(RoleName.CLIENTE)) {
            user.getRoles().clear();
            user.getRoles().add(request.getRole());
        }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            // Opcional: validar unicidad aquí
            user.setEmail(request.getEmail());
        }

        // 4. Guardar cambios
        User userActualizado = userDAO.actualizarUser(user);

        // 5. Devolver DTO
        return UserResponse.fromEntity(userActualizado);
    }

    @Override
    public void changePassword(Integer id, String newPassword) {
        User user = userDAO.obtenerUserPorId(id);
        if (user == null)
            throw new RuntimeException("Usuario no encontrado");

        // Encriptamos la nueva contraseña
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userDAO.actualizarUser(user);
    }

}
