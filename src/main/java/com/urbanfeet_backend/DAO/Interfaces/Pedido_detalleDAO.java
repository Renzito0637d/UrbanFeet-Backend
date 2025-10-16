package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Pedido_detalle;

public interface Pedido_detalleDAO {

    public List<Pedido_detalle> findAll();

    public void save(Pedido_detalle pedido_detalle);

    public Pedido_detalle findById(Integer id);

    public void update(Pedido_detalle pedido_detalle);

    public void deleteById(Integer id);

}
