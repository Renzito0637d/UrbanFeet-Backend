package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.Pedido_detalleDAO;
import com.urbanfeet_backend.Entity.Pedido_detalle;
import com.urbanfeet_backend.Services.Interfaces.Pedido_detalleService;

@Service
public class Pedido_detalleServiceImpl implements Pedido_detalleService {

    @Autowired
    private Pedido_detalleDAO detalleDao; // <-- Asegúrate que este nombre coincide con el DAO

    @Override
    public List<Pedido_detalle> obtenerTodo() {
        return detalleDao.findAll();
    }

    @Override
    public void guardar(Pedido_detalle detalle) {
        detalleDao.save(detalle);
    }

    @Override
    public Pedido_detalle buscarPorId(Integer id) {
        return detalleDao.findById(id);
    }

    @Override
    public void actualizar(Pedido_detalle detalle) {
        detalleDao.update(detalle);
    }

    @Override
    public void eliminarPorId(Integer id) {
        detalleDao.deleteById(id);
    }

    @Override
    public void eliminarPorPedidoId(Integer pedidoId) {
        detalleDao.deleteByPedidoId(pedidoId);
    }
}
