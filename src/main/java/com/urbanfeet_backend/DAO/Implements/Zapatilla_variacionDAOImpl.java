package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.Zapatilla_variacionDAO;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Exception.ResourceNotFoundException;
import com.urbanfeet_backend.Repository.ZapatillaRepository;
import com.urbanfeet_backend.Repository.Zapatilla_variacionRepository;

@Service
public class Zapatilla_variacionDAOImpl implements Zapatilla_variacionDAO {

    @Autowired
    private Zapatilla_variacionRepository zapatilla_variacionRepository;

    //verificar que la zapatilla padre exista
    @Autowired
    private ZapatillaRepository zapatillaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Zapatilla_variacion> findAll() {
        return zapatilla_variacionRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Zapatilla_variacion zapatilla_variacion) {
        if (zapatilla_variacion.getZapatilla() == null) {
             throw new IllegalStateException("No se puede guardar una variación sin una zapatilla asociada.");
        }
        zapatilla_variacionRepository.save(zapatilla_variacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Zapatilla_variacion findById(Integer id) {
        return zapatilla_variacionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Variación no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public Zapatilla_variacion update(Integer id, Zapatilla_variacion variacionDetails) {
        // 1. Encontrar la variación existente
        Zapatilla_variacion variacionExistente = findById(id);
        
        // 2. Actualizar solo los campos que nos interesan
        // (No tocamos el ID ni la Zapatilla padre)
        variacionExistente.setColor(variacionDetails.getColor());
        variacionExistente.setImageUrl(variacionDetails.getImageUrl());
        variacionExistente.setPrecio(variacionDetails.getPrecio());
        variacionExistente.setStock(variacionDetails.getStock());
        variacionExistente.setTalla(variacionDetails.getTalla());
        
        // 3. Guardar la entidad actualizada
        zapatilla_variacionRepository.save(variacionExistente);
        return variacionExistente;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!zapatilla_variacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar variación con id: " + id + " porque no existe.");
        }
        zapatilla_variacionRepository.deleteById(id);
    }

    // --- AÑADIR LA IMPLEMENTACIÓN DE ESTE MÉTODO ---
    @Override
    @Transactional(readOnly = true)
    public List<Zapatilla_variacion> findByZapatillaId(Integer zapatillaId) {
        // Primero, verificamos que la zapatilla padre exista
        if (!zapatillaRepository.existsById(zapatillaId)) {
            throw new ResourceNotFoundException("Zapatilla no encontrada con id: " + zapatillaId);
        }
        // Si existe, buscamos sus hijos
        return zapatilla_variacionRepository.findByZapatillaId(zapatillaId);
    }
}