package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Carrito;

public interface CarritoService {
    
    public List<Carrito> obtenerTodo();

    public Carrito guardar(Carrito carrito);

    public Carrito buscarPorId(Integer id);

    public Carrito actualizar(Carrito carrito);

    public void eliminarPorId(Integer id);
}
