package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Pedido_detalle;

public interface Pedido_detalleService {

    public List<Pedido_detalle> obtenerTodo();

    public void guardar(Pedido_detalle pedido_detalle);

    public Pedido_detalle buscarPorId(Integer id);

    public void actualizar(Pedido_detalle pedido_detalle);

    public void eliminarPorId(Integer id);

    void eliminarPorPedidoId(Integer pedidoId);

}
