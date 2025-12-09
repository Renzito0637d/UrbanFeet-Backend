package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;
import java.util.Optional;

import com.urbanfeet_backend.Entity.Sugerencia;

public interface SugerenciaDAO {

    public List<Sugerencia> listarSugerencias();

    public Optional<Sugerencia> obtenerPorId(Integer id);

    public Sugerencia guardar(Sugerencia sugerencia);

    public void eliminar(Integer id);
}
