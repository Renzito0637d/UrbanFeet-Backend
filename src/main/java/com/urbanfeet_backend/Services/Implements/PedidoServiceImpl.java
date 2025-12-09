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
    public List<Pedido> obtenerTodo() {
        return pedidoDao.findAll();
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

            // Validar y descontar stock
            if (variacion.getStock() < d.getCantidad()) {
                throw new RuntimeException("No hay stock suficiente");
            }
            variacion.setStock(variacion.getStock() - d.getCantidad());
            // variacionService.guardar(variacion); // Descomenta si tienes el método
            // guardar

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
                            // Datos extra para el Frontend:
                            zapatilla.getNombre(),
                            zapatilla.getMarca(),
                            variacion.getColor(),
                            variacion.getTalla());
                })
                .collect(Collectors.toList());

        Venta venta = ventaDAO.findByPedidoId(p.getId());
        String metodo = (venta != null) ? venta.getMetodoPago() : "Desconocido";

        return new PedidoResponseDTO(
                p.getId(),
                p.getUser().getId(),
                p.getEstado(),
                p.getFechaPedido(),
                DireccionEnvioDTO.fromEntity(p.getDireccion_envio()),
                detalles,
                metodo // <--- Pasamos el método de pago
        );
    }
}
