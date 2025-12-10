package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Venta;

public interface VentaDAO {

    public List<Venta> findAll();

    public void save(Venta venta);

    public Venta findById(Integer id);

    public void update(Venta venta);

    public void deleteById(Integer id);

    public Venta findByPedidoId(Integer pedidoId);
    
}
