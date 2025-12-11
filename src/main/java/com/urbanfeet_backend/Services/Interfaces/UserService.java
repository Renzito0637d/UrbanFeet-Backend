package com.urbanfeet_backend.Services.Interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.RoleName;
import com.urbanfeet_backend.Model.AuthDTOs.AuthResponse;
import com.urbanfeet_backend.Model.AuthDTOs.RegisterRequest;
import com.urbanfeet_backend.Model.UserDTOs.UserResponse;
import com.urbanfeet_backend.Model.UserDTOs.UserUpdateRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    public List<User> listarUsers();

    public User obtenerUserPorId(Integer id);

    public void guardarUser(User User);

    public void actualizarUser(User User);

    public void eliminarUser(Integer id);

    public AuthResponse registerCliente(RegisterRequest request);

    public AuthResponse registerAdmin(RegisterRequest request);

    public Page<UserResponse> listarPorRol(RoleName role, Pageable pageable);

    public Page<UserResponse> listarNoClientes(Pageable pageable);

    public UserResponse updateUser(Integer id, UserUpdateRequest request);

    public void changePassword(Integer id, String newPassword);

    public void exportUserPdf(HttpServletResponse response) throws IOException;

    public void exportUserExcel(HttpServletResponse response) throws IOException;

}
