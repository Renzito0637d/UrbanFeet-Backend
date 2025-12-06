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

        // ==================
        // DTOs (sin record)
        // ==================

        public static class PedidoRequestDTO {
                private Integer direccionId;
                private List<PedidoDetalleRequestDTO> detalles;

                public Integer getDireccionId() {
                        return direccionId;
                }

                public void setDireccionId(Integer direccionId) {
                        this.direccionId = direccionId;
                }

                public List<PedidoDetalleRequestDTO> getDetalles() {
                        return detalles;
                }

                public void setDetalles(List<PedidoDetalleRequestDTO> detalles) {
                        this.detalles = detalles;
                }
        }

        public static class PedidoDetalleRequestDTO {
                private Integer zapatillaVariacionId;
                private Integer cantidad;
                private Double precioTotal;

                public Integer getZapatillaVariacionId() {
                        return zapatillaVariacionId;
                }

                public void setZapatillaVariacionId(Integer zapatillaVariacionId) {
                        this.zapatillaVariacionId = zapatillaVariacionId;
                }

                public Integer getCantidad() {
                        return cantidad;
                }

                public void setCantidad(Integer cantidad) {
                        this.cantidad = cantidad;
                }

                public Double getPrecioTotal() {
                        return precioTotal;
                }

                public void setPrecioTotal(Double precioTotal) {
                        this.precioTotal = precioTotal;
                }
        }

        public static class PedidoResponseDTO {
                public Integer id;
                public Integer userId;
                public String estado;
                public LocalDateTime fechaPedido;
                public Direccion_envioDTO direccion_envio;
                public List<PedidoDetalleResponseDTO> detalles;

                public PedidoResponseDTO(Integer id, Integer userId, String estado,
                                LocalDateTime fechaPedido,
                                Direccion_envioDTO direccion_envio,
                                List<PedidoDetalleResponseDTO> detalles) {
                        this.id = id;
                        this.userId = userId;
                        this.estado = estado;
                        this.fechaPedido = fechaPedido;
                        this.direccion_envio = direccion_envio;
                        this.detalles = detalles;
                }
        }

        public static class PedidoDetalleResponseDTO {
                public Integer id;
                public Integer zapatillaVariacionId;
                public Integer cantidad;
                public Double precioTotal;

                public PedidoDetalleResponseDTO(Integer id, Integer zapatillaVariacionId,
                                Integer cantidad, Double precioTotal) {
                        this.id = id;
                        this.zapatillaVariacionId = zapatillaVariacionId;
                        this.cantidad = cantidad;
                        this.precioTotal = precioTotal;
                }
        }

        public static class Direccion_envioDTO {
                public String calle;
                public String distrito;
                public String provincia;
                public String departamento;
                public String referencia;

                public Direccion_envioDTO(String calle, String distrito, String provincia,
                                String departamento, String referencia) {
                        this.calle = calle;
                        this.distrito = distrito;
                        this.provincia = provincia;
                        this.departamento = departamento;
                        this.referencia = referencia;
                }

                public static Direccion_envioDTO fromEntity(Direccion_envio d) {
                        if (d == null)
                                return null;
                        return new Direccion_envioDTO(
                                        d.getCalle(),
                                        d.getDistrito(),
                                        d.getProvincia(),
                                        d.getDepartamento(),
                                        d.getReferencia());
                }
        }

        // ==================
        // ENDPOINTS
        // ==================

        @PostMapping
        public ResponseEntity<PedidoResponseDTO> crearPedido(
                        @RequestBody PedidoRequestDTO dto, Authentication authentication) {

                String email = authentication.getName();
                User user = userRepository.findUserByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                Direccion direccion = direccionService.buscarPorId(dto.getDireccionId());
                if (direccion == null)
                        throw new RuntimeException("La dirección no existe");

                if (!direccion.getUser().getId().equals(user.getId()))
                        throw new RuntimeException("Esa dirección no pertenece al usuario");

                Pedido pedido = pedidoService.crearPedido(user, direccion, dto.getDetalles());
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
                        @RequestBody PedidoRequestDTO dto,
                        Authentication authentication) {

                String email = authentication.getName();
                User user = userRepository.findUserByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                Pedido pedido = pedidoService.actualizarPedido(id, user, dto.getDetalles());
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
                                .map(d -> new PedidoDetalleResponseDTO(
                                                d.getId(),
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
}
