package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.CarritoItemDAO;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Repository.CarritoItemRepository;

@Repository
public class CarritoItemDAOImpl implements CarritoItemDAO {

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Override
    public List<Carrito_item> findAll() {
        return carritoItemRepository.findAll();
    }

    @Override
    public void save(Carrito_item carrito_item) {
        carritoItemRepository.save(carrito_item);
    }

    @Override
    public Carrito_item findById(Integer id) {
        return carritoItemRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Carrito_item carrito_item) {
        carritoItemRepository.save(carrito_item);
    }

    @Override
    public void deleteById(Integer id) {
        carritoItemRepository.deleteById(id);
    }

}
