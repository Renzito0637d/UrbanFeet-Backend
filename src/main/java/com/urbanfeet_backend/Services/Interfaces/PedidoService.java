package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Controller.PedidoController.PedidoDetalleRequestDTO;
import com.urbanfeet_backend.Entity.Pedido;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Direccion;

public interface PedidoService {

    List<Pedido> obtenerTodo();

    void guardar(Pedido pedido);

    Pedido buscarPorId(Integer id);

    void actualizar(Pedido pedido);

    void eliminarPorId(Integer id);

    List<Pedido> obtenerPedidosConDetallesPorUsuario(Integer userId);

    Pedido obtenerPedidoConDetallesPorId(Integer id);

    // Nuevos métodos con lógica de negocio
    Pedido crearPedido(User user, Direccion direccion, List<PedidoDetalleRequestDTO> detallesDTO);

    Pedido actualizarPedido(Integer id, User user, List<PedidoDetalleRequestDTO> detallesDTO);

    void eliminarPedido(Integer id, User user);
}
