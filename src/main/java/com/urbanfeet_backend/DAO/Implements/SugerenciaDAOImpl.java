package com.urbanfeet_backend.DAO.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.SugerenciaDAO;
import com.urbanfeet_backend.Entity.Sugerencia;
import com.urbanfeet_backend.Repository.SugerenciaRepository;

@Repository
public class SugerenciaDAOImpl implements SugerenciaDAO {

    private final SugerenciaRepository repository;

    public SugerenciaDAOImpl(SugerenciaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Sugerencia> listarSugerencias() {
        return repository.findAll();
    }

    @Override
    public Optional<Sugerencia> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Sugerencia guardar(Sugerencia sugerencia) {
        return repository.save(sugerencia);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

}
