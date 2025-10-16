package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Pedido;

public interface PedidoDAO {

    public List<Pedido> findAll();

    public void save(Pedido pedido);

    public Pedido findById(Integer id);

    public void update(Pedido pedido);

    public void deleteById(Integer id);
    
}
