package com.urbanfeet_backend.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.urbanfeet_backend.Entity.Direccion;
import com.urbanfeet_backend.Entity.Direccion_envio;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Model.DTOs.DireccionDTO;
import com.urbanfeet_backend.Model.DTOs.DireccionRequestDTO;
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

    private User getUser(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new RuntimeException("No autenticado");
        }
        return userRepository.findUserByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @PostMapping
    public ResponseEntity<DireccionDTO> create(@RequestBody DireccionRequestDTO dto, Authentication authentication) {
        User user = getUser(authentication);

        Direccion direccion = new Direccion();
        direccion.setCalle(dto.calle);
        direccion.setDistrito(dto.distrito);
        direccion.setProvincia(dto.provincia);
        direccion.setDepartamento(dto.departamento);
        direccion.setReferencia(dto.referencia);

        Direccion saved = direccionService.crearDireccion(direccion, user);
        return ResponseEntity.ok(toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<DireccionDTO>> list(Authentication authentication) {
        User user = getUser(authentication);
        List<DireccionDTO> dtos = direccionService.buscarPorUsuarioId(user.getId())
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> update(
            @PathVariable Integer id,
            @RequestBody DireccionRequestDTO dto,
            Authentication authentication) {

        User user = getUser(authentication);

        Direccion data = new Direccion();
        data.setCalle(dto.calle);
        data.setDistrito(dto.distrito);
        data.setProvincia(dto.provincia);
        data.setDepartamento(dto.departamento);
        data.setReferencia(dto.referencia);

        try {
            Direccion updated = direccionService.actualizarDireccion(id, data, user);
            if (updated == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(toDTO(updated));
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

    private DireccionDTO toDTO(Direccion d) {
        DireccionDTO dto = new DireccionDTO();
        dto.id = d.getId();
        dto.calle = d.getCalle();
        dto.distrito = d.getDistrito();
        dto.provincia = d.getProvincia();
        dto.departamento = d.getDepartamento();
        dto.referencia = d.getReferencia();
        return dto;
    }
}
