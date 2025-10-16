package com.urbanfeet_backend.DAO.Implements;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.CarritoDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Repository.CarritoRepository;

@Repository
public class CarritoDAOImpl implements CarritoDAO {

    @Autowired
    private CarritoRepository carritoRepository;

}
