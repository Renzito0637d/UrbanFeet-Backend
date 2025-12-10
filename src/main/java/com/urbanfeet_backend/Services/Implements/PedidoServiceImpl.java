package com.urbanfeet_backend.Services.Implements;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.PedidoDAO;
import com.urbanfeet_backend.DAO.Interfaces.VentaDAO;
import com.urbanfeet_backend.Entity.*;
import com.urbanfeet_backend.Services.Interfaces.PedidoService;
import com.urbanfeet_backend.Services.Interfaces.Pedido_detalleService;
import com.urbanfeet_backend.Services.Interfaces.VentaService;
import com.urbanfeet_backend.Services.Interfaces.Zapatilla_variacionService;
import com.urbanfeet_backend.Model.DTOs.DireccionEnvioDTO;
import com.urbanfeet_backend.Model.DTOs.PedidoDetalleRequestDTO;
import com.urbanfeet_backend.Model.DTOs.PedidoDetalleResponseDTO;
import com.urbanfeet_backend.Model.DTOs.PedidoRequestDTO;
import com.urbanfeet_backend.Model.DTOs.PedidoResponseDTO;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoDAO pedidoDao;

    @Autowired
    private VentaDAO ventaDAO;

    @Autowired
    private Pedido_detalleService detalleService;

    @Autowired
    private Zapatilla_variacionService variacionService;

    @Autowired
    private VentaService ventaService;

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obtenerTodosLosPedidos() {
        // 1. Obtenemos todas las entidades de la base de datos
        List<Pedido> pedidos = pedidoDao.findAll();

        // 2. Las convertimos a DTO usando tu método helper existente
        return pedidos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void guardar(Pedido pedido) {
        pedidoDao.save(pedido);
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        return pedidoDao.findById(id);
    }

    @Override
    public void actualizar(Pedido pedido) {
        pedidoDao.update(pedido);
    }

    @Override
    public void eliminarPorId(Integer id) {
        pedidoDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true) // Mantiene la sesión abierta
    public List<PedidoResponseDTO> obtenerPedidosConDetallesPorUsuario(Integer userId) {
        List<Pedido> pedidos = pedidoDao.findAllWithDetallesByUserId(userId);

        // Convertimos a DTO aquí dentro
        return pedidos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // --- IMPLEMENTACIÓN CORREGIDA 2 ---
    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO obtenerPedidoConDetallesPorId(Integer id, Integer userId) {
        Pedido pedido = pedidoDao.findByIdWithDetalles(id);

        if (pedido == null)
            return null;

        // Validación de seguridad (el pedido debe pertenecer al usuario)
        if (!pedido.getUser().getId().equals(userId)) {
            throw new RuntimeException("No autorizado");
        }

        return mapToDTO(pedido);
    }

    @Override
    @Transactional // IMPORTANTE: Si falla la venta, se borra el pedido
    public PedidoResponseDTO crearPedido(User user, Direccion direccion,
            List<PedidoDetalleRequestDTO> detallesDTO,
            String metodoPago) {

        // 1. Lógica de Crear Pedido (IGUAL QUE ANTES)
        Pedido pedido = new Pedido();
        pedido.setUser(user);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        pedido.setDireccion_envio(new Direccion_envio(
                direccion.getCalle(), direccion.getDistrito(), direccion.getProvincia(),
                direccion.getDepartamento(), direccion.getReferencia()));
        pedidoDao.save(pedido);

        // 2. Lógica de Detalles y Stock (IGUAL QUE ANTES)
        double sumaTotal = 0.0;
        List<Pedido_detalle> detalles = new ArrayList<>();

        for (PedidoDetalleRequestDTO d : detallesDTO) {
            Pedido_detalle detalle = new Pedido_detalle();
            detalle.setPedido(pedido);

            Zapatilla_variacion variacion = variacionService.buscarPorId(d.getZapatillaVariacionId());
            if (variacion == null)
                throw new RuntimeException("Variación no encontrada");

            if (variacion.getStock() < d.getCantidad()) {
                throw new RuntimeException("Sin stock suficiente para: " + variacion.getZapatilla().getNombre());
            }

            variacion.setStock(variacion.getStock() - d.getCantidad());

            variacionService.actualizar(variacion);

            detalle.setZapatilla_variacion(variacion);
            detalle.setCantidad(d.getCantidad());

            double precioReal = variacion.getPrecio() * d.getCantidad();
            detalle.setPrecioTotal(precioReal);
            sumaTotal += precioReal;

            detalleService.guardar(detalle);
            detalles.add(detalle);
        }
        pedido.setDetalles(detalles);
        pedidoDao.update(pedido);

        // 3. Registrar Venta (IGUAL QUE ANTES)
        Venta venta = new Venta();
        venta.setPedido(pedido);
        venta.setFecha(LocalDate.now());
        venta.setHora(LocalTime.now());
        venta.setEstadoPago("COMPLETADO");
        venta.setMetodoPago(metodoPago);
        venta.setMontoPagado(BigDecimal.valueOf(sumaTotal));
        ventaService.guardar(venta);

        // 4. CAMBIO AQUÍ: Convertir a DTO antes de salir
        // Como estamos dentro de @Transactional, podemos leer los nombres de zapatillas
        // sin error
        return mapToDTO(pedido);
    }

    @Override
    @Transactional
    public Pedido actualizarPedido(Integer id, User user, List<PedidoDetalleRequestDTO> detallesDTO) {

        // CORRECCIÓN AQUÍ:
        // Usamos pedidoDao directamente para obtener la ENTIDAD, no el método del DTO.
        Pedido pedido = pedidoDao.findByIdWithDetalles(id);

        if (pedido == null)
            throw new RuntimeException("Pedido no encontrado");

        if (!pedido.getUser().getId().equals(user.getId()))
            throw new RuntimeException("No autorizado para actualizar este pedido");

        // Limpiar detalles anteriores
        detalleService.eliminarPorPedidoId(pedido.getId());

        // Crear nuevos detalles
        List<Pedido_detalle> detalles = detallesDTO.stream().map(d -> {
            Pedido_detalle detalle = new Pedido_detalle();
            detalle.setPedido(pedido);
            Zapatilla_variacion variacion = variacionService.buscarPorId(d.getZapatillaVariacionId());
            detalle.setZapatilla_variacion(variacion);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioTotal(d.getPrecioTotal());
            detalleService.guardar(detalle);
            return detalle;
        }).collect(Collectors.toList());

        pedido.setDetalles(detalles);
        pedidoDao.update(pedido);

        return pedido;
    }

    @Override
    @Transactional
    public void eliminarPedido(Integer id, User user) {
        // CORRECCIÓN AQUÍ TAMBIÉN:
        // Usamos pedidoDao directamente para obtener la ENTIDAD.
        Pedido pedido = pedidoDao.findByIdWithDetalles(id);

        if (pedido == null)
            throw new RuntimeException("Pedido no encontrado");

        if (!pedido.getUser().getId().equals(user.getId()))
            throw new RuntimeException("No autorizado para eliminar este pedido");

        pedidoDao.deleteById(id);
    }

    private PedidoResponseDTO mapToDTO(Pedido p) {
        List<PedidoDetalleResponseDTO> detalles = p.getDetalles().stream()
                .map(d -> {
                    var variacion = d.getZapatilla_variacion();
                    var zapatilla = variacion.getZapatilla();
                    return new PedidoDetalleResponseDTO(
                            d.getId(),
                            variacion.getId(),
                            d.getCantidad(),
                            d.getPrecioTotal(),
                            zapatilla.getNombre(),
                            zapatilla.getMarca(),
                            variacion.getColor(),
                            variacion.getTalla());
                })
                .collect(Collectors.toList());

        Venta venta = ventaDAO.findByPedidoId(p.getId());
        String metodo = (venta != null) ? venta.getMetodoPago() : "Desconocido";

        // Obtenemos el usuario para sacar sus datos
        User u = p.getUser();

        return new PedidoResponseDTO(
                p.getId(),
                u.getId(),
                p.getEstado(),
                p.getFechaPedido(),
                DireccionEnvioDTO.fromEntity(p.getDireccion_envio()),
                detalles,
                metodo,
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getPhone());
    }

    @Override
    @Transactional
    public void cancelarPedido(Integer id, User user) {
        // 1. Buscar el pedido
        Pedido pedido = pedidoDao.findByIdWithDetalles(id);
        if (pedido == null)
            throw new RuntimeException("Pedido no encontrado");

        // 2. Validaciones (Usuario, Estado actual...)
        if (!pedido.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No autorizado");
        }
        if ("CANCELADO".equals(pedido.getEstado())) {
            throw new RuntimeException("El pedido ya está cancelado");
        }
        // ... otras validaciones

        // 3. RESTAURAR STOCK (Tu lógica existente)
        for (Pedido_detalle detalle : pedido.getDetalles()) {
            Zapatilla_variacion variacion = detalle.getZapatilla_variacion();
            int stockRestaurado = variacion.getStock() + detalle.getCantidad();
            variacion.setStock(stockRestaurado);
            variacionService.actualizar(variacion);
        }

        // 4. CAMBIAR ESTADO DEL PEDIDO
        pedido.setEstado("CANCELADO");
        pedidoDao.update(pedido);

        // 5. ACTUALIZAR LA VENTA A "REEMBOLSADO" (NUEVO)
        Venta venta = ventaDAO.findByPedidoId(id);
        if (venta != null) {
            venta.setEstadoPago("REEMBOLSADO"); // O "ANULADO"
            ventaDAO.save(venta); // Guardamos el cambio
        }
    }

    @Override
    @Transactional
    public void actualizarEstado(Integer id, String nuevoEstado) {
        Pedido pedido = pedidoDao.findById(id); // No necesitamos detalles para esto, carga rápida
        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado");
        }

        // Aquí puedes agregar validaciones de transición de estado si quieres
        // Ej: No pasar de ENTREGADO a PENDIENTE

        pedido.setEstado(nuevoEstado);
        pedidoDao.update(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO actualizarPedidoAdmin(Integer id, PedidoRequestDTO dto) {
        // 1. Buscar el pedido
        Pedido pedido = pedidoDao.findByIdWithDetalles(id);
        if (pedido == null)
            throw new RuntimeException("Pedido no encontrado");

        if ("CANCELADO".equals(dto.getEstado()) && !"CANCELADO".equals(pedido.getEstado())) {

            // A. Restaurar Stock
            for (Pedido_detalle detalle : pedido.getDetalles()) {
                Zapatilla_variacion variacion = detalle.getZapatilla_variacion();
                variacion.setStock(variacion.getStock() + detalle.getCantidad());
                variacionService.actualizar(variacion);
            }

            // B. Reembolsar Venta
            Venta venta = ventaDAO.findByPedidoId(id);
            if (venta != null) {
                venta.setEstadoPago("REEMBOLSADO");
                ventaDAO.save(venta);
            }

            pedido.setEstado("CANCELADO");
            pedidoDao.update(pedido);
            return mapToDTO(pedido);
        }

        // C. Actualizar Estado
        if (dto.getEstado() != null && !dto.getEstado().isEmpty()) {
            pedido.setEstado(dto.getEstado());
        }

        // D. Actualizar Método de Pago (Solo texto en la venta)
        if (dto.getMetodoPago() != null) {
            Venta venta = ventaDAO.findByPedidoId(id);
            if (venta != null) {
                venta.setMetodoPago(dto.getMetodoPago());
                ventaDAO.save(venta);
            }
        }

        pedidoDao.update(pedido);

        return mapToDTO(pedido);
    }
}
