package com.urbanfeet_backend.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.urbanfeet_backend.Entity.*;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Services.Interfaces.PedidoService;
import com.urbanfeet_backend.Services.Interfaces.DireccionService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

        private final PedidoService pedidoService;
        private final DireccionService direccionService;
        private final UserRepository userRepository;

        public PedidoController(PedidoService pedidoService,
                        DireccionService direccionService,
                        UserRepository userRepository) {
                this.pedidoService = pedidoService;
                this.direccionService = direccionService;
                this.userRepository = userRepository;
        }

        // DTOs
        public record PedidoRequestDTO(List<PedidoDetalleRequestDTO> detalles) {
        }

        public record PedidoDetalleRequestDTO(Integer zapatillaVariacionId, Integer cantidad, Double precioTotal) {
        }

        public record PedidoResponseDTO(Integer id, Integer userId, String estado, LocalDateTime fechaPedido,
                        Direccion_envioDTO direccion_envio, List<PedidoDetalleResponseDTO> detalles) {
        }

        public record PedidoDetalleResponseDTO(Integer id, Integer zapatillaVariacionId, Integer cantidad,
                        Double precioTotal) {
        }

        public record Direccion_envioDTO(String calle, String distrito, String provincia, String departamento,
                        String referencia) {
                public static Direccion_envioDTO fromEntity(Direccion_envio d) {
                        if (d == null)
                                return null;
                        return new Direccion_envioDTO(d.getCalle(), d.getDistrito(), d.getProvincia(),
                                        d.getDepartamento(), d.getReferencia());
                }
        }

        @PostMapping
        public ResponseEntity<PedidoResponseDTO> crearPedido(@RequestBody PedidoRequestDTO dto,
                        Authentication authentication) {
                String email = authentication.getName();
                User user = userRepository.findUserByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                Direccion direccion = direccionService.buscarPorUsuarioId(user.getId())
                                .stream()
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Usuario no tiene dirección registrada"));

                Pedido pedido = pedidoService.crearPedido(user, direccion, dto.detalles());
                return ResponseEntity.ok(mapToDTO(pedido));
        }

        @GetMapping
        public ResponseEntity<List<PedidoResponseDTO>> listarPedidos(Authentication authentication) {
                String email = authentication.getName();
                User user = userRepository.findUserByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                List<Pedido> pedidos = pedidoService.obtenerPedidosConDetallesPorUsuario(user.getId());
                List<PedidoResponseDTO> pedidosDTO = pedidos.stream().map(this::mapToDTO).toList();
                return ResponseEntity.ok(pedidosDTO);
        }

        @GetMapping("/{id}")
        public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Integer id,
                        Authentication authentication) {
                Pedido pedido = pedidoService.obtenerPedidoConDetallesPorId(id);
                if (pedido == null)
                        return ResponseEntity.notFound().build();

                String email = authentication.getName();
                if (!pedido.getUser().getEmail().equals(email))
                        return ResponseEntity.status(403).build();

                return ResponseEntity.ok(mapToDTO(pedido));
        }

        @PutMapping("/{id}")
        public ResponseEntity<PedidoResponseDTO> actualizarPedido(@PathVariable Integer id,
                        @RequestBody PedidoRequestDTO dto, Authentication authentication) {
                String email = authentication.getName();
                User user = userRepository.findUserByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                Pedido pedido = pedidoService.actualizarPedido(id, user, dto.detalles());
                return ResponseEntity.ok(mapToDTO(pedido));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id, Authentication authentication) {
                String email = authentication.getName();
                User user = userRepository.findUserByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                pedidoService.eliminarPedido(id, user);
                return ResponseEntity.noContent().build();
        }

        private PedidoResponseDTO mapToDTO(Pedido p) {
                List<PedidoDetalleResponseDTO> detalles = p.getDetalles().stream()
                                .map(d -> new PedidoDetalleResponseDTO(d.getId(), d.getZapatilla_variacion().getId(),
                                                d.getCantidad(), d.getPrecioTotal()))
                                .toList();

                return new PedidoResponseDTO(p.getId(), p.getUser().getId(), p.getEstado(), p.getFechaPedido(),
                                Direccion_envioDTO.fromEntity(p.getDireccion_envio()), detalles);
        }
}
