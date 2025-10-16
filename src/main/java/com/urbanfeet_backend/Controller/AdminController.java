package com.urbanfeet_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Model.AuthResponse;
import com.urbanfeet_backend.Model.RegisterRequest;
import com.urbanfeet_backend.Services.Interfaces.UserService;
import com.urbanfeet_backend.Services.Interfaces.ZapatillaService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ZapatillaService zapatillaService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok().body(userService.registerAdmin(request));
    }

    @GetMapping("/usuarios")
    public List<User> todosLosUsuarios() {
        return userService.listarUsers();
    }

    @PostMapping("/crearZapatilla")
    public ResponseEntity<Zapatilla> crearZapatilla(@RequestBody Zapatilla zapatilla) {
        zapatillaService.guardar(zapatilla);
        return ResponseEntity.ok(zapatilla);
    }

}
