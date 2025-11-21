package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.Pedido_detalleDAO;
import com.urbanfeet_backend.Entity.Pedido_detalle;
import com.urbanfeet_backend.Repository.Pedido_detalleRepository;

@Repository
public class Pedido_detalleDAOImpl implements Pedido_detalleDAO {

    @Autowired
    private Pedido_detalleRepository pedido_detalleRepository;

    @Override
    public List<Pedido_detalle> findAll() {
        return pedido_detalleRepository.findAll();
    }

    @Override
    public void save(Pedido_detalle pedido_detalle) {
        pedido_detalleRepository.save(pedido_detalle);
    }

    @Override
    public Pedido_detalle findById(Integer id) {
        return pedido_detalleRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Pedido_detalle pedido_detalle) {
        pedido_detalleRepository.save(pedido_detalle);
    }

    @Override
    public void deleteById(Integer id) {
        pedido_detalleRepository.deleteById(id);
    }

    @Override
    public void deleteByPedidoId(Integer pedidoId) {
        // Busca los detalles de ese pedido
        List<Pedido_detalle> detalles = pedido_detalleRepository.findByPedidoId(pedidoId);
        // Elimina todos los detalles encontrados
        pedido_detalleRepository.deleteAll(detalles);
    }

    @Override
    public List<Pedido_detalle> findByPedidoId(Integer pedidoId) {
        return pedido_detalleRepository.findByPedidoId(pedidoId);
    }
}
