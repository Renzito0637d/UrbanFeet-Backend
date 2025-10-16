package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Direccion;

public interface DireccionDAO {
    
    public List<Direccion> findAll();

    public void save(Direccion direccion);

    public Direccion findById(Integer id);

    public void update(Direccion direccion);

    public void deleteById(Integer id);

}
