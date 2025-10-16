package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.DireccionDAO;
import com.urbanfeet_backend.Entity.Direccion;
import com.urbanfeet_backend.Repository.DireccionRepository;

@Repository
public class DireccionDAOImpl implements DireccionDAO {

    @Autowired
    private DireccionRepository direccionRepository;

    @Override
    public List<Direccion> findAll() {
        return direccionRepository.findAll();
    }

    @Override
    public void save(Direccion direccion) {
        direccionRepository.save(direccion);
    }

    @Override
    public Direccion findById(Integer id) {
        return direccionRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Direccion direccion) {
        direccionRepository.save(direccion);
    }

    @Override
    public void deleteById(Integer id) {
        direccionRepository.deleteById(id);
    }

    @Override
    public List<Direccion> findByUserId(Integer userId) {
        return direccionRepository.findByUser_Id(userId);
    }
    
}
