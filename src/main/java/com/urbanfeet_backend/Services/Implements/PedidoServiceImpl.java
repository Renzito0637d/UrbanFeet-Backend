package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.PedidoDAO;
import com.urbanfeet_backend.Entity.Pedido;
import com.urbanfeet_backend.Services.Interfaces.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoDAO pedidoDao;

    @Override
    public List<Pedido> obtenerTodo() {
        return pedidoDao.findAll();
    }

    @Override
    public void guardar(Pedido pedido) {
        pedidoDao.save(pedido);
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        return pedidoDao.findById(id);
    }

    @Override
    public void actualizar(Pedido pedido) {
        pedidoDao.update(pedido);
    }

    @Override
    public void eliminarPorId(Integer id) {
        pedidoDao.deleteById(id);
    }

    // Nuevo método
    @Override
    public List<Pedido> obtenerPedidosConDetallesPorUsuario(Integer userId) {
        return pedidoDao.findAllWithDetallesByUserId(userId);
    }

    @Override
    public Pedido obtenerPedidoConDetallesPorId(Integer id) {
        return pedidoDao.findByIdWithDetalles(id);
    }

}
