package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Carrito;

public interface CarritoDAO {
    
    public List<Carrito> findAll();

    public Carrito save(Carrito carrito);

    public Carrito findById(Integer id);

    public Carrito update(Carrito carrito);

    public void deleteById(Integer id);
}