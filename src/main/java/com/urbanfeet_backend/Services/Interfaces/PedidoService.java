package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Pedido;

public interface PedidoService {

    public List<Pedido> obtenerTodo();

    public void guardar(Pedido pedido);

    public Pedido buscarPorId(Integer id);

    public void actualizar(Pedido pedido);

    public void eliminarPorId(Integer id);

}
