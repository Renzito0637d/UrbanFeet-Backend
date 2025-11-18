package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.stereotype.Service;

import com.urbanfeet_backend.Entity.Direccion;
import com.urbanfeet_backend.Repository.DireccionRepository;
import com.urbanfeet_backend.Services.Interfaces.DireccionService;

@Service
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository direccionRepository;

    public DireccionServiceImpl(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    @Override
    public List<Direccion> obtenerTodo() {
        return direccionRepository.findAll();
    }

    @Override
    public void guardar(Direccion direccion) {
        direccionRepository.save(direccion);
    }

    @Override
    public Direccion buscarPorId(Integer id) {
        return direccionRepository.findById(id)
                .orElse(null);
    }

    @Override
    public void actualizar(Direccion direccion) {
        direccionRepository.save(direccion);
    }

    @Override
    public void eliminarPorId(Integer id) {
        direccionRepository.deleteById(id);
    }

    @Override
    public List<Direccion> buscarPorUsuarioId(Integer userId) {
        return direccionRepository.findByUser_Id(userId);
    }
}
