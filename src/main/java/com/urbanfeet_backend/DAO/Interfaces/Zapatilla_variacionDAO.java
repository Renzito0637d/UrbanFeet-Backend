package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Zapatilla_variacion;

public interface Zapatilla_variacionDAO {

    public List<Zapatilla_variacion> findAll();

    public void save(Zapatilla_variacion zapatilla_variacion);

    public Zapatilla_variacion findById(Integer id);

    public void update(Zapatilla_variacion zapatilla_variacion);

    public void deleteById(Integer id);

}
