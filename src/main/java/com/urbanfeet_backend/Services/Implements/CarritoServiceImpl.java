package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.CarritoDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Services.Interfaces.CarritoService;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoDAO carritoDao;

    @Override
    public List<Carrito> obtenerTodo() {
        return carritoDao.findAll();
    }

    @Override
    public void guardar(Carrito carrito) {
        carritoDao.save(carrito);
    }

    @Override
    public Carrito buscarPorId(Integer id) {
        return carritoDao.findById(id);
    }

    @Override
    public void actualizar(Carrito carrito) {
        carritoDao.update(carrito);
    }

    @Override
    public void eliminarPorId(Integer id) {
        carritoDao.deleteById(id);
    }
}