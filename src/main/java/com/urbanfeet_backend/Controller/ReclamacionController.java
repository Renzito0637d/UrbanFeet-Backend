package com.urbanfeet_backend.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.urbanfeet_backend.Entity.Reclamacion;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Model.ReclamacionDTOs.ReclamacionRequestDTO;
import com.urbanfeet_backend.Model.ReclamacionDTOs.ReclamacionResponseDTO;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Services.Interfaces.ReclamacionService;

@RestController
@RequestMapping("/reclamaciones")
public class ReclamacionController {

    private final ReclamacionService reclamacionService;
    private final UserRepository userRepository;

    public ReclamacionController(ReclamacionService reclamacionService, UserRepository userRepository) {
        this.reclamacionService = reclamacionService;
        this.userRepository = userRepository;
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> obtenerDatosUsuario(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(new Object() {
            public final String nombre = user.getNombre() + " " + user.getApellido();
            public final String documento = user.getDocumentNumber();
            public final String domicilio = "";
            public final String correo = user.getEmail();
            public final String telefono = user.getPhone();
        });
    }

    @PostMapping
    public ResponseEntity<ReclamacionResponseDTO> crear(
            @RequestBody ReclamacionRequestDTO dto,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Reclamacion r = new Reclamacion();
        r.setProducto(dto.producto);
        r.setMontoReclamado(dto.montoReclamado);
        r.setTipoMensaje(dto.tipoMensaje);
        r.setDetalleReclamo(dto.detalleReclamo);
        r.setSolucionPropuesta(dto.solucionPropuesta);
        r.setDireccion(dto.direccion);

        Reclamacion creado = reclamacionService.crearReclamacion(r, user.getId());
        return ResponseEntity.ok(toResponse(creado));
    }

    @GetMapping("/mis-reclamos")
    public ResponseEntity<List<ReclamacionResponseDTO>> misReclamos(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Reclamacion> lista = reclamacionService.obtenerMisReclamaciones(user.getId());
        return ResponseEntity.ok(lista.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @GetMapping
    public ResponseEntity<List<ReclamacionResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                reclamacionService.obtenerTodos()
                        .stream().map(this::toResponse)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReclamacionResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(toResponse(reclamacionService.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReclamacionResponseDTO> actualizar(
            @PathVariable Integer id,
            @RequestBody ReclamacionRequestDTO dto,
            Authentication authentication) {

        Reclamacion data = new Reclamacion();
        data.setProducto(dto.producto);
        data.setMontoReclamado(dto.montoReclamado);
        data.setTipoMensaje(dto.tipoMensaje);
        data.setDetalleReclamo(dto.detalleReclamo);
        data.setSolucionPropuesta(dto.solucionPropuesta);

        data.setEstado(dto.estado);

        return ResponseEntity.ok(toResponse(reclamacionService.actualizar(id, data)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id, Authentication authentication) {
        reclamacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private ReclamacionResponseDTO toResponse(Reclamacion r) {
        ReclamacionResponseDTO dto = new ReclamacionResponseDTO();
        dto.id = r.getId() != null ? r.getId() : null;
        dto.producto = r.getProducto();
        dto.montoReclamado = r.getMontoReclamado();
        dto.tipoMensaje = r.getTipoMensaje();
        dto.detalleReclamo = r.getDetalleReclamo();
        dto.solucionPropuesta = r.getSolucionPropuesta();
        dto.fechaRegistro = r.getFechaRegistro() != null ? r.getFechaRegistro().toString() : null;
        dto.estado = r.getEstado();
        dto.userId = r.getUser() != null ? r.getUser().getId() : null;
        dto.direccion = r.getDireccion();

        if (r.getUser() != null) {
            dto.nombreUsuario = r.getUser().getNombre() + " " + r.getUser().getApellido();
            dto.documentoUsuario = r.getUser().getDocumentNumber();
            dto.emailUsuario = r.getUser().getEmail();
            dto.telefonoUsuario = r.getUser().getPhone();
        }

        return dto;
    }
}
