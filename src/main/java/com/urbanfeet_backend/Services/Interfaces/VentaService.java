package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Venta;

public interface VentaService {

    public List<Venta> obtenerTodo();

    public void guardar(Venta venta);

    public Venta buscarPorId(Integer id);

    public void actualizar(Venta venta);

    public void eliminarPorId(Integer id);

}
