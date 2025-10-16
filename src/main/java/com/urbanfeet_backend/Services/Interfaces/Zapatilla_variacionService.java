package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Zapatilla_variacion;

public interface Zapatilla_variacionService {

    public List<Zapatilla_variacion> obtenerTodo();

    public void guardar(Zapatilla_variacion zapatilla_variacion);

    public Zapatilla_variacion buscarPorId(Integer id);

    public void actualizar(Zapatilla_variacion zapatilla_variacion);

    public void eliminarPorId(Integer id);

}
