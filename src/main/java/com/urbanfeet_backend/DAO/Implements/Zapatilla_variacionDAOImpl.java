package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.Zapatilla_variacionDAO;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Repository.Zapatilla_variacionRepository;

@Repository
public class Zapatilla_variacionDAOImpl implements Zapatilla_variacionDAO {

    @Autowired
    private Zapatilla_variacionRepository zapatilla_variacionRepository;

    @Override
    public List<Zapatilla_variacion> findAll() {
        return zapatilla_variacionRepository.findAll();
    }

    @Override
    public void save(Zapatilla_variacion zapatilla_variacion) {
        zapatilla_variacionRepository.save(zapatilla_variacion);
    }

    @Override
    public Zapatilla_variacion findById(Integer id) {
        return zapatilla_variacionRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Zapatilla_variacion zapatilla_variacion) {
        zapatilla_variacionRepository.save(zapatilla_variacion);
    }

    @Override
    public void deleteById(Integer id) {
        zapatilla_variacionRepository.deleteById(id);
    }
    
}
