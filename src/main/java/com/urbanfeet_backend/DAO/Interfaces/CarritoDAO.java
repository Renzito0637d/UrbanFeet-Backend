package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Carrito;

public interface CarritoDAO {
    
    public List<Carrito> findAll();

    public void save(Carrito carrito);

    public Carrito findById(Integer id);

    public void update(Carrito carrito);

    public void deleteById(Integer id);
}