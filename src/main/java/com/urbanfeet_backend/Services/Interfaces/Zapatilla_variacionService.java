package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;

public interface Zapatilla_variacionService {

    List<Zapatilla_variacion> obtenerTodo();

    Zapatilla_variacion guardar(Zapatilla_variacion variacion); // retorna entidad

    Zapatilla_variacion obtenerPorId(Integer id);

    Zapatilla_variacion actualizar(Integer id, Zapatilla_variacion variacion);

    void eliminarPorId(Integer id);

    List<Zapatilla_variacion> findByZapatillaId(Integer zapatillaId);
}
