package com.urbanfeet_backend.Services.Implements;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.PedidoDAO;
import com.urbanfeet_backend.Entity.*;
import com.urbanfeet_backend.Services.Interfaces.PedidoService;
import com.urbanfeet_backend.Services.Interfaces.Pedido_detalleService;
import com.urbanfeet_backend.Services.Interfaces.Zapatilla_variacionService;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoDAO pedidoDao;

    @Autowired
    private Pedido_detalleService detalleService;

    @Autowired
    private Zapatilla_variacionService variacionService;

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
    public List<Pedido> obtenerPedidosConDetallesPorUsuario(Integer userId) {
        return pedidoDao.findAllWithDetallesByUserId(userId);
    }

    @Override
    public Pedido obtenerPedidoConDetallesPorId(Integer id) {
        return pedidoDao.findByIdWithDetalles(id);
    }

    // ---------------- Lógica de negocio ----------------

    @Override
    public Pedido crearPedido(User user, Direccion direccion,
            List<com.urbanfeet_backend.Controller.PedidoController.PedidoDetalleRequestDTO> detallesDTO) {
        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setUser(user);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        pedido.setDireccion_envio(new Direccion_envio(
                direccion.getCalle(),
                direccion.getDistrito(),
                direccion.getProvincia(),
                direccion.getDepartamento(),
                direccion.getReferencia()));
        this.guardar(pedido);

        // Crear detalles
        List<Pedido_detalle> detalles = detallesDTO.stream().map(d -> {
            Pedido_detalle detalle = new Pedido_detalle();
            detalle.setPedido(pedido);

            Zapatilla_variacion variacion = variacionService.obtenerPorId(d.zapatillaVariacionId());
            if (variacion == null)
                throw new RuntimeException("Variación no encontrada: " + d.zapatillaVariacionId());
            detalle.setZapatilla_variacion(variacion);

            detalle.setCantidad(d.cantidad());
            detalle.setPrecioTotal(d.precioTotal());

            detalleService.guardar(detalle);
            return detalle;
        }).toList();

        pedido.setDetalles(detalles);
        this.actualizar(pedido);

        return pedido;
    }

    @Override
    public Pedido actualizarPedido(Integer id, User user,
            List<com.urbanfeet_backend.Controller.PedidoController.PedidoDetalleRequestDTO> detallesDTO) {
        Pedido pedido = this.obtenerPedidoConDetallesPorId(id);
        if (pedido == null)
            throw new RuntimeException("Pedido no encontrado");

        if (!pedido.getUser().getId().equals(user.getId()))
            throw new RuntimeException("No autorizado para actualizar este pedido");

        // Eliminar detalles anteriores
        detalleService.eliminarPorPedidoId(pedido.getId());

        // Crear nuevos detalles
        List<Pedido_detalle> detalles = detallesDTO.stream().map(d -> {
            Pedido_detalle detalle = new Pedido_detalle();
            detalle.setPedido(pedido);

            Zapatilla_variacion variacion = variacionService.obtenerPorId(d.zapatillaVariacionId());
            if (variacion == null)
                throw new RuntimeException("Variación no encontrada: " + d.zapatillaVariacionId());
            detalle.setZapatilla_variacion(variacion);

            detalle.setCantidad(d.cantidad());
            detalle.setPrecioTotal(d.precioTotal());

            detalleService.guardar(detalle);
            return detalle;
        }).toList();

        pedido.setDetalles(detalles);
        this.actualizar(pedido);

        return pedido;
    }

    @Override
    public void eliminarPedido(Integer id, User user) {
        Pedido pedido = this.obtenerPedidoConDetallesPorId(id);
        if (pedido == null)
            throw new RuntimeException("Pedido no encontrado");

        if (!pedido.getUser().getId().equals(user.getId()))
            throw new RuntimeException("No autorizado para eliminar este pedido");

        this.eliminarPorId(id);
    }
}
