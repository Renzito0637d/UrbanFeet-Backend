package com.urbanfeet_backend.Dao.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.Dao.Interfaces.CarritoItemDao;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Repository.CarritoItemRepository;

@Repository
public class CarritoItemDaoImpl implements CarritoItemDao {

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Override
    public List<Carrito_item> findByCarrito(Carrito carrito) {
        return carritoItemRepository.findByCarrito(carrito);
    }

    @Override
    public Carrito_item save(Carrito_item item) {
        return carritoItemRepository.save(item);
    }

    @Override
    public void delete(Carrito_item item) {
        carritoItemRepository.delete(item);
    }
}
