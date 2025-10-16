package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Zapatilla;

public interface ZapatillaService {

    public List<Zapatilla> obtenerTodo();

    public void guardar(Zapatilla zapatilla);

    public Zapatilla buscarPorId(Integer id);

    public void actualizar(Zapatilla zapatilla);

    public void eliminarPorId(Integer id);

}
