package com.urbanfeet_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.RoleName;
import com.urbanfeet_backend.Model.AuthDTOs.AuthResponse;
import com.urbanfeet_backend.Model.AuthDTOs.RegisterRequest;
import com.urbanfeet_backend.Model.UserDTOs.ChangePasswordRequest;
import com.urbanfeet_backend.Model.UserDTOs.UserResponse;
import com.urbanfeet_backend.Model.UserDTOs.UserUpdateRequest;
import com.urbanfeet_backend.Services.Interfaces.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET CLIENTES (Devuelve DTO)
    @GetMapping("/clients")
    public ResponseEntity<Page<UserResponse>> getClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(userService.listarPorRol(RoleName.CLIENTE, pageable));
    }

    // GET INTERNOS (Devuelve DTO)
    @GetMapping("/internal")
    public ResponseEntity<Page<UserResponse>> getInternalUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(userService.listarNoClientes(pageable));
    }

    @PostMapping("/clients")
    public ResponseEntity<AuthResponse> createClient(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerCliente(request));
    }

    @PostMapping("/internal")
    public ResponseEntity<AuthResponse> createInternalUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerAdmin(request));
    }

    // Este puede seguir devolviendo User si es para detalle individual y lo manejas
    // con cuidado,
    // pero idealmente también debería ser UserResponse.
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.obtenerUserPorId(id));
    }

    // Endpoint para eliminar (lo necesitabas en Angular)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.eliminarUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Integer id,
            @RequestBody UserUpdateRequest request) {

        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Integer id,
            @RequestBody ChangePasswordRequest request) {

        userService.changePassword(id, request.getPassword());
        return ResponseEntity.noContent().build();
    }

}
