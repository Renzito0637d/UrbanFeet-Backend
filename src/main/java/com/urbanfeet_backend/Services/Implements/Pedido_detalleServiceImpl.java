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
    private Pedido_detalleDAO pedido_detalleDao;

    @Override
    public List<Pedido_detalle> obtenerTodo() {
        return pedido_detalleDao.findAll();
    }

    @Override
    public void guardar(Pedido_detalle pedido_detalle) {
        pedido_detalleDao.save(pedido_detalle);
    }

    @Override
    public Pedido_detalle buscarPorId(Integer id) {
        return pedido_detalleDao.findById(id);
    }

    @Override
    public void actualizar(Pedido_detalle pedido_detalle) {
        pedido_detalleDao.update(pedido_detalle);
    }

    @Override
    public void eliminarPorId(Integer id) {
        pedido_detalleDao.deleteById(id);
    }

}