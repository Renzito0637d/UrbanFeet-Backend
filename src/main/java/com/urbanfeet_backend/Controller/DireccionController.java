package com.urbanfeet_backend.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.urbanfeet_backend.Entity.Direccion;
import com.urbanfeet_backend.Entity.Direccion_envio;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Services.Interfaces.DireccionService;

@RestController
@RequestMapping("/directions")
@CrossOrigin(origins = "*")
public class DireccionController {

    private final DireccionService direccionService;
    private final UserRepository userRepository;

    public DireccionController(DireccionService direccionService, UserRepository userRepository) {
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
    }

    /**
     * Obtiene al usuario autenticado desde el JWT (email).
     */
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
        direccion.setUser(user);

        direccionService.guardar(direccion);

        DireccionDTO dto = new DireccionDTO(
                direccion.getId(),
                direccion.getCalle(),
                direccion.getDistrito(),
                direccion.getProvincia(),
                direccion.getDepartamento(),
                direccion.getReferencia());

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<DireccionDTO>> list(Authentication authentication) {
        User user = getUser(authentication);

        List<DireccionDTO> dtos = direccionService.buscarPorUsuarioId(user.getId())
                .stream()
                .map(d -> new DireccionDTO(
                        d.getId(),
                        d.getCalle(),
                        d.getDistrito(),
                        d.getProvincia(),
                        d.getDepartamento(),
                        d.getReferencia()))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> update(@PathVariable Integer id,
            @RequestBody Direccion data,
            Authentication authentication) {
        User user = getUser(authentication);
        Direccion exist = direccionService.buscarPorId(id);

        if (exist == null)
            return ResponseEntity.notFound().build();

        if (!exist.getUser().getId().equals(user.getId()))
            return ResponseEntity.status(403).build();

        exist.setCalle(data.getCalle());
        exist.setDistrito(data.getDistrito());
        exist.setProvincia(data.getProvincia());
        exist.setDepartamento(data.getDepartamento());
        exist.setReferencia(data.getReferencia());

        direccionService.actualizar(exist);

        DireccionDTO dto = new DireccionDTO(
                exist.getId(),
                exist.getCalle(),
                exist.getDistrito(),
                exist.getProvincia(),
                exist.getDepartamento(),
                exist.getReferencia());

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id, Authentication authentication) {
        User user = getUser(authentication);
        Direccion exist = direccionService.buscarPorId(id);

        if (exist == null)
            return ResponseEntity.notFound().build();

        if (!exist.getUser().getId().equals(user.getId()))
            return ResponseEntity.status(403).build();

        direccionService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/direccion-envio")
    public ResponseEntity<Direccion_envio> obtenerDireccionEnvio(Authentication auth) {
        User user = getUser(auth); // método que obtiene el usuario logueado
        List<Direccion> direcciones = direccionService.buscarPorUsuarioId(user.getId());
        Direccion primera = direcciones.stream().findFirst().orElse(null);

        Direccion_envio envio = null;
        if (primera != null) {
            envio = new Direccion_envio(
                    primera.getCalle(),
                    primera.getDistrito(),
                    primera.getProvincia(),
                    primera.getDepartamento(),
                    primera.getReferencia());
        }

        return ResponseEntity.ok(envio);
    }

}
