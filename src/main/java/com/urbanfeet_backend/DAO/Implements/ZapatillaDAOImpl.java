package com.urbanfeet_backend.DAO.Implements;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.ZapatillaDAO;
import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Entity.Zapatilla_variacion; 
import com.urbanfeet_backend.Exception.ResourceNotFoundException; 
import com.urbanfeet_backend.Repository.ZapatillaRepository;

@Service 
public class ZapatillaDAOImpl implements ZapatillaDAO {

    @Autowired
    private ZapatillaRepository zapatillaRepository;

    @Override
    @Transactional(readOnly = true) 
    public List<Zapatilla> findAll() {
        return zapatillaRepository.findAll();
    }

    @Override
    @Transactional 
    public void save(Zapatilla zapatilla) {
// Sincroniza la relación: asegura que cada variación "sepa" quién es su padre.
        if (zapatilla.getVariaciones() != null) {
            for (Zapatilla_variacion variacion : zapatilla.getVariaciones()) {
                variacion.setZapatilla(zapatilla);
            }
        }
        zapatillaRepository.save(zapatilla);
    }

    @Override
    @Transactional(readOnly = true)
    public Zapatilla findById(Integer id) {
        // Nunca devuelvas null. Lanza una excepción que el handler manejará.
        return zapatillaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Zapatilla no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public void update(Zapatilla zapatilla) {
        // La lógica de actualizar es la misma que la de guardar.
                
        // Primero, verifica que exista
        Zapatilla zapatillaExistente = zapatillaRepository.findById(zapatilla.getId())
             .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar zapatilla con id: " + zapatilla.getId() + " porque no existe."));

        // Sincroniza las nuevas variaciones
        if (zapatilla.getVariaciones() != null) {
            for (Zapatilla_variacion variacion : zapatilla.getVariaciones()) {
                variacion.setZapatilla(zapatilla);
            }
        }
        
        // El método save() de JPA actualiza si el ID ya existe.
        zapatillaRepository.save(zapatilla);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // Verifica si existe antes de borrar para poder lanzar la excepción
        if (!zapatillaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar zapatilla con id: " + id + " porque no existe.");
        }
        zapatillaRepository.deleteById(id);
    }
}