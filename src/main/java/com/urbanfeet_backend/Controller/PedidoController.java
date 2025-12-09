package com.urbanfeet_backend.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.urbanfeet_backend.Entity.*;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Services.Interfaces.PedidoService;
import com.urbanfeet_backend.Services.Interfaces.DireccionService;

// Importar los DTOs
import com.urbanfeet_backend.Model.DTOs.PedidoRequestDTO;
import com.urbanfeet_backend.Model.DTOs.PedidoResponseDTO;

@RestController
@RequestMapping("/pedidos")
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

        // --- CREAR PEDIDO (POST) ---
        @PostMapping
        public ResponseEntity<PedidoResponseDTO> crearPedido(@RequestBody PedidoRequestDTO dto,
                        Authentication authentication) {

                User user = getUser(authentication);
                Direccion direccion = direccionService.buscarPorId(dto.getDireccionId());

                if (direccion == null)
                        throw new RuntimeException("La dirección no existe");
                if (!direccion.getUser().getId().equals(user.getId()))
                        throw new RuntimeException("Dirección inválida");

                // El servicio devuelve el DTO directamente (Transacción OK)
                PedidoResponseDTO response = pedidoService.crearPedido(
                                user,
                                direccion,
                                dto.getDetalles(),
                                dto.getMetodoPago());

                return ResponseEntity.ok(response);
        }

        // --- LISTAR PEDIDOS (GET) ---
        @GetMapping
        public ResponseEntity<List<PedidoResponseDTO>> listarPedidos(Authentication authentication) {
                User user = getUser(authentication);
                // El servicio devuelve DTOs (Transacción OK)
                return ResponseEntity.ok(pedidoService.obtenerPedidosConDetallesPorUsuario(user.getId()));
        }

        // --- OBTENER UN PEDIDO (GET /{id}) ---
        @GetMapping("/{id}")
        public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Integer id,
                        Authentication authentication) {
                User user = getUser(authentication);
                try {
                        PedidoResponseDTO dto = pedidoService.obtenerPedidoConDetallesPorId(id, user.getId());
                        if (dto == null)
                                return ResponseEntity.notFound().build();
                        return ResponseEntity.ok(dto);
                } catch (RuntimeException e) {
                        return ResponseEntity.status(403).build();
                }
        }

        // --- ACTUALIZAR PEDIDO (PUT) ---
        @PutMapping("/{id}")
        public ResponseEntity<?> actualizarPedido(@PathVariable Integer id,
                        @RequestBody PedidoRequestDTO dto,
                        Authentication authentication) {

                User user = getUser(authentication);

                // CORRECCIÓN AQUÍ:
                // Si el servicio 'actualizarPedido' devuelve una Entidad, NO la mapees aquí.
                // Lo ideal sería que el servicio devolviera void o el DTO.
                // Si devuelve Entidad, solo confirmamos con OK o llamamos de nuevo a
                // 'obtenerPedido' (seguro).

                pedidoService.actualizarPedido(id, user, dto.getDetalles());

                // Opción segura: Volver a pedir el DTO transaccional
                PedidoResponseDTO response = pedidoService.obtenerPedidoConDetallesPorId(id, user.getId());

                return ResponseEntity.ok(response);
        }

        // --- ELIMINAR PEDIDO (DELETE) ---
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id,
                        Authentication authentication) {

                User user = getUser(authentication);
                pedidoService.eliminarPedido(id, user);
                return ResponseEntity.noContent().build();
        }

        private User getUser(Authentication auth) {
                return userRepository.findUserByEmail(auth.getName())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }
}
