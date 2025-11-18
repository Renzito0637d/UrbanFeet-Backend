package com.urbanfeet_backend.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.urbanfeet_backend.Entity.*;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Services.Interfaces.PedidoService;
import com.urbanfeet_backend.Services.Interfaces.Pedido_detalleService;
import com.urbanfeet_backend.Services.Interfaces.DireccionService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

        private final PedidoService pedidoService;
        private final Pedido_detalleService detalleService;
        private final DireccionService direccionService;
        private final UserRepository userRepository;

        public PedidoController(PedidoService pedidoService, Pedido_detalleService detalleService,
                        DireccionService direccionService, UserRepository userRepository) {
                this.pedidoService = pedidoService;
                this.detalleService = detalleService;
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
                                        d.getDepartamento(),
                                        d.getReferencia());
                }
        }

        // <-- Usuario fijo para pruebas
        private User getTestUser() {
                return userRepository.findById(1)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }

        @PostMapping
        public ResponseEntity<PedidoResponseDTO> crearPedido(@RequestBody PedidoRequestDTO dto) {
                User user = getTestUser();

                Direccion direccion = direccionService.buscarPorUsuarioId(user.getId())
                                .stream().findFirst()
                                .orElseThrow(() -> new RuntimeException("Usuario no tiene dirección registrada"));

                Direccion_envio direccion_envio = new Direccion_envio(
                                direccion.getCalle(),
                                direccion.getDistrito(),
                                direccion.getProvincia(),
                                direccion.getDepartamento(),
                                direccion.getReferencia());

                Pedido pedido = new Pedido();
                pedido.setUser(user);
                pedido.setFechaPedido(LocalDateTime.now());
                pedido.setEstado("PENDIENTE");
                pedido.setDireccion_envio(direccion_envio);
                pedidoService.guardar(pedido);

                List<Pedido_detalle> detalles = dto.detalles().stream().map(d -> {
                        Pedido_detalle detalle = new Pedido_detalle();
                        detalle.setPedido(pedido);

                        Zapatilla_variacion var = new Zapatilla_variacion();
                        var.setId(d.zapatillaVariacionId());
                        detalle.setZapatilla_variacion(var);

                        detalle.setCantidad(d.cantidad());
                        detalle.setPrecioTotal(d.precioTotal());
                        detalleService.guardar(detalle);
                        return detalle;
                }).toList();

                pedido.setDetalles(detalles);
                pedidoService.actualizar(pedido);

                return ResponseEntity.ok(mapToDTO(pedido));
        }

        // Map a DTO
        private PedidoResponseDTO mapToDTO(Pedido p) {
                List<PedidoDetalleResponseDTO> detalles = p.getDetalles().stream()
                                .map(d -> new PedidoDetalleResponseDTO(d.getId(),
                                                d.getZapatilla_variacion().getId(),
                                                d.getCantidad(),
                                                d.getPrecioTotal()))
                                .toList();

                return new PedidoResponseDTO(
                                p.getId(),
                                p.getUser().getId(),
                                p.getEstado(),
                                p.getFechaPedido(),
                                Direccion_envioDTO.fromEntity(p.getDireccion_envio()),
                                detalles);
        }

        @GetMapping
        public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
                User user = getTestUser();

                // Ahora usamos el método que trae detalles junto con los pedidos
                List<Pedido> pedidos = pedidoService.obtenerPedidosConDetallesPorUsuario(user.getId());

                List<PedidoResponseDTO> pedidosDTO = pedidos.stream()
                                .map(this::mapToDTO)
                                .toList();

                return ResponseEntity.ok(pedidosDTO);
        }

        @GetMapping("/{id}")
        public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Integer id) {
                // Obtenemos el pedido con detalles
                Pedido pedido = pedidoService.obtenerPedidoConDetallesPorId(id);

                if (pedido == null) {
                        // Si no existe, lanzamos excepción
                        throw new RuntimeException("Pedido no encontrado");
                }

                return ResponseEntity.ok(mapToDTO(pedido));
        }

        @PutMapping("/{id}")
        public ResponseEntity<PedidoResponseDTO> actualizarPedido(@PathVariable Integer id,
                        @RequestBody PedidoRequestDTO dto) {
                // Obtenemos el pedido con detalles
                Pedido pedido = pedidoService.obtenerPedidoConDetallesPorId(id);

                if (pedido == null) {
                        throw new RuntimeException("Pedido no encontrado");
                }

                // Actualizamos los detalles (eliminar y crear de nuevo)
                detalleService.eliminarPorPedidoId(pedido.getId());

                List<Pedido_detalle> detalles = dto.detalles().stream().map(d -> {
                        Pedido_detalle detalle = new Pedido_detalle();
                        detalle.setPedido(pedido);

                        Zapatilla_variacion var = new Zapatilla_variacion();
                        var.setId(d.zapatillaVariacionId());
                        detalle.setZapatilla_variacion(var);

                        detalle.setCantidad(d.cantidad());
                        detalle.setPrecioTotal(d.precioTotal());
                        detalleService.guardar(detalle);
                        return detalle;
                }).toList();

                pedido.setDetalles(detalles);
                pedidoService.actualizar(pedido);

                return ResponseEntity.ok(mapToDTO(pedido));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
                Pedido pedido = pedidoService.obtenerPedidoConDetallesPorId(id);

                if (pedido == null) {
                        throw new RuntimeException("Pedido no encontrado");
                }

                pedidoService.eliminarPorId(id);
                return ResponseEntity.noContent().build();
        }

}
