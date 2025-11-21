package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Pedido_detalle;

public interface Pedido_detalleDAO {

    List<Pedido_detalle> findAll();

    void save(Pedido_detalle pedido_detalle);

    Pedido_detalle findById(Integer id);

    void update(Pedido_detalle pedido_detalle);

    void deleteById(Integer id);

    void deleteByPedidoId(Integer pedidoId);

    List<Pedido_detalle> findByPedidoId(Integer pedidoId);
}
