package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.CarritoItemDAO;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Services.Interfaces.CarritoItemService;

@Service
public class CarritoItemServiceImpl implements CarritoItemService {

    @Autowired
    private CarritoItemDAO carritoItemDao;

    @Override
    public List<Carrito_item> obtenerTodo() {
        return carritoItemDao.findAll();
    }

    @Override
    public void guardar(Carrito_item carrito_item) {
        carritoItemDao.save(carrito_item);
    }

    @Override
    public Carrito_item buscarPorId(Integer id) {
        return carritoItemDao.findById(id);
    }

    @Override
    public void actualizar(Carrito_item carrito_item) {
        carritoItemDao.update(carrito_item);
    }

    @Override
    public void eliminarPorId(Integer id) {
        carritoItemDao.deleteById(id);
    }

}
