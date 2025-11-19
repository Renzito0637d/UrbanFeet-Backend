package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Direccion;

public interface DireccionService {

    public List<Direccion> obtenerTodo();

    public void guardar(Direccion direccion);

    public Direccion buscarPorId(Integer id);

    public void actualizar(Direccion direccion);

    public void eliminarPorId(Integer id);

    public List<Direccion> buscarPorUsuarioId(Integer userId);

}
