package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.ZapatillaDAO;
import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Repository.ZapatillaRepository;

@Repository
public class ZapatillaDAOImpl implements ZapatillaDAO {

    @Autowired
    private ZapatillaRepository zapatillaRepository;

    @Override
    public List<Zapatilla> findAll() {
        return zapatillaRepository.findAll();
    }

    @Override
    public void save(Zapatilla zapatilla) {
        zapatillaRepository.save(zapatilla);
    }

    @Override
    public Zapatilla findById(Integer id) {
        return zapatillaRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Zapatilla zapatilla) {
        zapatillaRepository.save(zapatilla);
    }

    @Override
    public void deleteById(Integer id) {
        zapatillaRepository.deleteById(id);
    }
    
}
