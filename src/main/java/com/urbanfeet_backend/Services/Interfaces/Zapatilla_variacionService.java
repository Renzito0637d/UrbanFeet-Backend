package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionRequest;
import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionResponse;

public interface Zapatilla_variacionService {

    VariacionResponse crearVariacion(Integer zapatillaId, VariacionRequest request);

    List<Zapatilla_variacion> obtenerTodo();

    Zapatilla_variacion guardar(Zapatilla_variacion variacion);

    Zapatilla_variacion buscarPorId(Integer id);

    VariacionResponse actualizar(Integer id, VariacionRequest request);

    void eliminarPorId(Integer id);

    List<VariacionResponse> findByZapatillaId(Integer zapatillaId);
}
