package com.urbanfeet_backend.Services.Implements;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.Dao.Interfaces.CarritoDao;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Services.Interfaces.CarritoService;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoDao carritoDao;

    @Override
    public Optional<Carrito> getCarritoByUser(User user) {
        return carritoDao.findByUser(user);
    }

    @Override
    public Carrito saveCarrito(Carrito carrito) {
        return carritoDao.save(carrito);
    }

    @Override
    public void deleteCarrito(Carrito carrito) {
        carritoDao.delete(carrito);
    }
}
