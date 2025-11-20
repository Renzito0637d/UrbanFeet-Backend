package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Direccion;

public interface DireccionService {

    List<Direccion> obtenerTodo();

    void guardar(Direccion direccion);

    Direccion buscarPorId(Integer id);

    void actualizar(Direccion direccion);

    void eliminarPorId(Integer id);

    List<Direccion> buscarPorUsuarioId(Integer userId);

}
