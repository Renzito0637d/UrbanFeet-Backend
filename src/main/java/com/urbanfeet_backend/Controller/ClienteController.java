package com.urbanfeet_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanfeet_backend.Entity.Direccion;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Services.Interfaces.DireccionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private DireccionService direccionService;

    @PostMapping("/savedireccion")
    public ResponseEntity<Direccion> guardarDireccionDelUsuario(@RequestBody Direccion direccion,
            Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User usuarioActual = (User) principal;

            // 3. Asignar el objeto Usuario a la dirección
            direccion.setUser(usuarioActual); // o setUser(usuarioActual)

            // 4. Guardar la dirección con la referencia al usuario
            direccionService.guardar(direccion);

            return ResponseEntity.ok(direccion);
        } else {
            // Si el principal no es del tipo esperado, es un error del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public record DireccionRecord(
            Integer idDireccion,
            String calle,
            String distrito,
            String provincia,
            String departamento,
            String referencia) {
    }

    @GetMapping("/mis-direcciones")
    public ResponseEntity<List<DireccionRecord>> obtenerDireccionesDelUsuario(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            User usuarioActual = (User) principal;
            Integer usuarioId = usuarioActual.getId();

            List<Direccion> direcciones = direccionService.buscarPorUsuarioId(usuarioId);

            // Convertimos la lista de Entidades a una lista de Records
            List<DireccionRecord> direccionesRecord = direcciones.stream()
                    .map(direccion -> new DireccionRecord(
                            direccion.getId(),
                            direccion.getCalle(),
                            direccion.getDistrito(),
                            direccion.getProvincia(),
                            direccion.getDepartamento(),
                            direccion.getReferencia()))
                    .toList();

            return ResponseEntity.ok(direccionesRecord);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
