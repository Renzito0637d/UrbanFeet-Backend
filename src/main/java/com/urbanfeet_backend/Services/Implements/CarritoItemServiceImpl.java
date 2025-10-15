package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.CarritoItemDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Services.Interfaces.CarritoItemService;

@Service
public class CarritoItemServiceImpl implements CarritoItemService {

    @Autowired
    private CarritoItemDAO carritoItemDao;

    @Override
    public List<Carrito_item> getItemsByCarrito(Carrito carrito) {
        return carritoItemDao.findByCarrito(carrito);
    }

    @Override
    public Carrito_item saveItem(Carrito_item item) {
        return carritoItemDao.save(item);
    }

    @Override
    public void deleteItem(Carrito_item item) {
        carritoItemDao.delete(item);
    }
}
