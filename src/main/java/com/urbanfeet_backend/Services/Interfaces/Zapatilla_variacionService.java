package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionRequest;
import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionResponse;
import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionUpdateRequest;

public interface Zapatilla_variacionService {

    List<VariacionResponse> crearVariacion(Integer zapatillaId, VariacionRequest request);

    List<Zapatilla_variacion> obtenerTodo();

    Zapatilla_variacion guardar(Zapatilla_variacion variacion);

    Zapatilla_variacion buscarPorId(Integer id);

    VariacionResponse actualizar(Integer id, VariacionUpdateRequest request);

    void eliminarPorId(Integer id);

    List<VariacionResponse> findByZapatillaId(Integer zapatillaId);
}
