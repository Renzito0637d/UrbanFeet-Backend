package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.Zapatilla_variacionDAO;
import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Repository.ZapatillaRepository;
import com.urbanfeet_backend.Repository.Zapatilla_variacionRepository;

@Repository
public class Zapatilla_variacionDAOImpl implements Zapatilla_variacionDAO {

    @Autowired
    private Zapatilla_variacionRepository variacionRepository;

    @Autowired
    private ZapatillaRepository zapatillaRepository;

    @Override
    public List<Zapatilla_variacion> findAll() {
        return variacionRepository.findAll();
    }

    @Override
    public Zapatilla_variacion save(Zapatilla_variacion variacion) {
        return variacionRepository.save(variacion);
    }

    @Override
    public Zapatilla_variacion findById(Integer id) {
        return variacionRepository.findById(id).orElse(null);
    }

    @Override
    public Zapatilla_variacion update(Integer id, Zapatilla_variacion variacion) {
        Zapatilla_variacion original = variacionRepository.findById(id).orElse(null);

        if (original == null) return null;

        original.setColor(variacion.getColor());
        original.setImageUrl(variacion.getImageUrl());
        original.setPrecio(variacion.getPrecio());
        original.setStock(variacion.getStock());
        original.setTalla(variacion.getTalla());

        return variacionRepository.save(original);
    }

    @Override
    public void deleteById(Integer id) {
        variacionRepository.deleteById(id);
    }

    @Override
    public List<Zapatilla_variacion> findByZapatillaId(Integer zapatillaId) {
        Zapatilla zap = zapatillaRepository.findById(zapatillaId).orElse(null);
        if (zap == null) return null;

        return zap.getVariaciones();
    }

    @Override
    public void actualizar(Zapatilla_variacion variacion) {
        // Simplemente llamamos al DAO para que guarde los cambios (stock, etc.)
        variacionRepository.save(variacion); 
    }
}
