package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.ReclamacionDAO;
import com.urbanfeet_backend.Entity.Reclamacion;
import com.urbanfeet_backend.Repository.ReclamacionRepository;

@Repository
public class ReclamacionDAOImpl implements ReclamacionDAO {

    @Autowired
    private ReclamacionRepository reclamacionRepository;

    @Override
    public List<Reclamacion> findAll() {
        return reclamacionRepository.findAll();
    }

    @Override
    public void save(Reclamacion reclamacion) {
        reclamacionRepository.save(reclamacion);
    }

    @Override
    public Reclamacion findById(Integer id) {
        return reclamacionRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Reclamacion reclamacion) {
        reclamacionRepository.save(reclamacion);
    }

    @Override
    public void deleteById(Integer id) {
        reclamacionRepository.deleteById(id);
    }

    @Override
    public List<Reclamacion> findByUserId(Integer userId) {
        return reclamacionRepository.findByUser_Id(userId);
    }
}
