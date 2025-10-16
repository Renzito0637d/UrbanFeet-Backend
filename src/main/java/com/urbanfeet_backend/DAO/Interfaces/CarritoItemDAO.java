package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Carrito_item;

public interface CarritoItemDAO {
    
    public List<Carrito_item> findAll();

    public void save(Carrito_item carrito_item);

    public Carrito_item findById(Integer id);

    public void update(Carrito_item carrito_item);

    public void deleteById(Integer id);

}
