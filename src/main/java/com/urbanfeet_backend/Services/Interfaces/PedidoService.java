package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Model.DTOs.PedidoDetalleRequestDTO;
import com.urbanfeet_backend.Model.DTOs.PedidoResponseDTO;
import com.urbanfeet_backend.Entity.Pedido;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Direccion;

public interface PedidoService {

    List<Pedido> obtenerTodo();

    void guardar(Pedido pedido);

    Pedido buscarPorId(Integer id);

    void actualizar(Pedido pedido);

    void eliminarPorId(Integer id);

    List<PedidoResponseDTO> obtenerPedidosConDetallesPorUsuario(Integer userId);

    PedidoResponseDTO obtenerPedidoConDetallesPorId(Integer id, Integer userId);

    // Nuevos métodos con lógica de negocio
    PedidoResponseDTO crearPedido(User user, Direccion direccion, List<PedidoDetalleRequestDTO> detallesDTO,
            String metodoPago);

    Pedido actualizarPedido(Integer id, User user, List<PedidoDetalleRequestDTO> detallesDTO);

    void eliminarPedido(Integer id, User user);
}
