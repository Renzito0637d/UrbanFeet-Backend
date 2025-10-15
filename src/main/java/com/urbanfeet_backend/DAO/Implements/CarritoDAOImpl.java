package com.urbanfeet_backend.DAO.Implements;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.Dao.Interfaces.CarritoDao;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Repository.CarritoRepository;

@Repository
public class CarritoDaoImpl implements CarritoDao {

    @Autowired
    private CarritoRepository carritoRepository;

    @Override
    public Optional<Carrito> findByUser(User user) {
        return carritoRepository.findByUser(user);
    }

    @Override
    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    @Override
    public void delete(Carrito carrito) {
        carritoRepository.delete(carrito);
    }
}
