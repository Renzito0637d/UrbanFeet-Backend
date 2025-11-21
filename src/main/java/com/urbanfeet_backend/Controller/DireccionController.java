package com.urbanfeet_backend.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.urbanfeet_backend.Entity.Direccion;
import com.urbanfeet_backend.Entity.Direccion_envio;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Services.Implements.DireccionServiceImpl;

@RestController
@RequestMapping("/directions")
public class DireccionController {

    private final DireccionServiceImpl direccionService;
    private final UserRepository userRepository;

    public DireccionController(DireccionServiceImpl direccionService, UserRepository userRepository) {
        this.direccionService = direccionService;
        this.userRepository = userRepository;
    }

    public record DireccionDTO(
            Integer id,
            String calle,
            String distrito,
            String provincia,
            String departamento,
            String referencia) {
        public static DireccionDTO fromEntity(Direccion d) {
            return new DireccionDTO(
                    d.getId(),
                    d.getCalle(),
                    d.getDistrito(),
                    d.getProvincia(),
                    d.getDepartamento(),
                    d.getReferencia());
        }
    }

    private User getUser(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new RuntimeException("No autenticado");
        }
        return userRepository.findUserByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @PostMapping
    public ResponseEntity<DireccionDTO> create(@RequestBody Direccion direccion, Authentication authentication) {
        User user = getUser(authentication);
        Direccion saved = direccionService.crearDireccion(direccion, user);
        return ResponseEntity.ok(DireccionDTO.fromEntity(saved));
    }

    @GetMapping
    public ResponseEntity<List<DireccionDTO>> list(Authentication authentication) {
        User user = getUser(authentication);
        List<DireccionDTO> dtos = direccionService.buscarPorUsuarioId(user.getId())
                .stream().map(DireccionDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> update(@PathVariable Integer id,
            @RequestBody Direccion data,
            Authentication authentication) {
        User user = getUser(authentication);
        try {
            Direccion updated = direccionService.actualizarDireccion(id, data, user);
            if (updated == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(DireccionDTO.fromEntity(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id, Authentication authentication) {
        User user = getUser(authentication);
        try {
            boolean deleted = direccionService.eliminarDireccion(id, user);
            if (!deleted)
                return ResponseEntity.notFound().build();
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/direccion-envio")
    public ResponseEntity<Direccion_envio> obtenerDireccionEnvio(Authentication authentication) {
        User user = getUser(authentication);
        Direccion_envio envio = direccionService.obtenerPrimeraDireccionEnvio(user);
        return ResponseEntity.ok(envio);
    }
}
