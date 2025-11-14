package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;

public interface Zapatilla_variacionService {

    public List<Zapatilla_variacion> obtenerTodo();

    public void guardar(Zapatilla_variacion zapatilla_variacion);

    public Zapatilla_variacion buscarPorId(Integer id);

    // acepte el ID y los detalles
    public Zapatilla_variacion actualizar(Integer id, Zapatilla_variacion zapatilla_variacion);

    public void eliminarPorId(Integer id);

    public List<Zapatilla_variacion> findByZapatillaId(Integer zapatillaId);
}