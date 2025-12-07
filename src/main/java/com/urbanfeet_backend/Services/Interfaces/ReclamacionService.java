package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Reclamacion;

public interface ReclamacionService {

    Reclamacion crearReclamacion(Reclamacion r, Integer userId);

    List<Reclamacion> obtenerMisReclamaciones(Integer userId);

    List<Reclamacion> obtenerTodos();

    Reclamacion obtenerPorId(Integer id);

    Reclamacion actualizar(Integer id, Reclamacion data);

    void eliminar(Integer id);
}
