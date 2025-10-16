package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.PedidoDAO;
import com.urbanfeet_backend.Entity.Pedido;
import com.urbanfeet_backend.Repository.PedidoRepository;

@Repository
public class PedidoDAOImpl implements PedidoDAO {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public void save(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Override
    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Override
    public void deleteById(Integer id) {
        pedidoRepository.deleteById(id);
    }
    
}
