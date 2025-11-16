package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.CarritoDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Repository.CarritoRepository;

@Repository
public class CarritoDAOImpl implements CarritoDAO {

    @Autowired
    private CarritoRepository carritoRepository;

    @Override
    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }

    @Override
    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito findById(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }

    @Override
    public Carrito update(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    @Override
    public void deleteById(Integer id) {
        carritoRepository.deleteById(id);
    }
}
