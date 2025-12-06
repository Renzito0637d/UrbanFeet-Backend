package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Reclamacion;

public interface ReclamacionDAO {

    List<Reclamacion> findAll();

    void save(Reclamacion reclamacion);

    Reclamacion findById(Integer id);

    void update(Reclamacion reclamacion);

    void deleteById(Integer id);

    List<Reclamacion> findByUserId(Integer userId);
}
