package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Carrito_item;

public interface CarritoItemService {
    
    public List<Carrito_item> obtenerTodo();

    public void guardar(Carrito_item carrito_item);

    public Carrito_item buscarPorId(Integer id);

    public void actualizar(Carrito_item carrito_item);

    public void eliminarPorId(Integer id);

}
