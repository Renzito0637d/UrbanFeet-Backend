package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.Carrito_item;

public interface CarritoItemDAO {
    List<Carrito_item> findByCarrito(Carrito carrito);
    Carrito_item save(Carrito_item item);
    void delete(Carrito_item item);
}
