package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Carrito;

public interface CarritoService {
    
    public List<Carrito> obtenerTodo();

    public void guardar(Carrito carrito);

    public Carrito buscarPorId(Integer id);

    public void actualizar(Carrito carrito);

    public void eliminarPorId(Integer id);
}
