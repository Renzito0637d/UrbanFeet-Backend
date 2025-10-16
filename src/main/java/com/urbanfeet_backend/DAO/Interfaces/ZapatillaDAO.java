package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Zapatilla;

public interface ZapatillaDAO {
    
    public List<Zapatilla> findAll();

    public void save(Zapatilla zapatilla);

    public Zapatilla findById(Integer id);

    public void update(Zapatilla zapatilla);

    public void deleteById(Integer id);

}
