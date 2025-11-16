package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;

public interface Zapatilla_variacionDAO {

    List<Zapatilla_variacion> findAll();

    Zapatilla_variacion save(Zapatilla_variacion variacion); // retorna entidad

    Zapatilla_variacion findById(Integer id);

    Zapatilla_variacion update(Integer id, Zapatilla_variacion variacion);

    void deleteById(Integer id);

    List<Zapatilla_variacion> findByZapatillaId(Integer zapatillaId);
}
