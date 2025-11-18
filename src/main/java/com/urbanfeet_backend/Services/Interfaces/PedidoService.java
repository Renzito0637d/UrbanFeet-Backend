package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Pedido;

public interface PedidoService {

    List<Pedido> obtenerTodo();

    void guardar(Pedido pedido);

    Pedido buscarPorId(Integer id);

    void actualizar(Pedido pedido);

    void eliminarPorId(Integer id);

    List<Pedido> obtenerPedidosConDetallesPorUsuario(Integer userId);

    Pedido obtenerPedidoConDetallesPorId(Integer id);

}
