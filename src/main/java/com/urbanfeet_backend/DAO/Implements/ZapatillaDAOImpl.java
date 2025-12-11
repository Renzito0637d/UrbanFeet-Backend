package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.ZapatillaDAO;
import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Repository.ZapatillaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class ZapatillaDAOImpl implements ZapatillaDAO {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ZapatillaRepository zapatillaRepository;

    @Override
    public Page<Zapatilla> findAll(Pageable pageable) {
        return zapatillaRepository.findAll(pageable);
    }

    @Override
    public Zapatilla save(Zapatilla zapatilla) {
        em.persist(zapatilla);
        return zapatilla; // retorna la entidad ya con ID
    }

    @Override
    public Zapatilla findById(Integer id) {
        return em.find(Zapatilla.class, id);
    }

    @Override
    public Zapatilla update(Zapatilla zapatilla) {
        return em.merge(zapatilla);
    }

    @Override
    public void deleteById(Integer id) {
        Zapatilla z = findById(id);
        if (z != null) {
            em.remove(z);
        }
    }

    @Override
    public Page<Zapatilla> findByVariacionesIsNotEmpty(Pageable pageable) {
        return zapatillaRepository.findByVariacionesIsNotEmpty(pageable);
    }

    @Override
    public Page<Zapatilla> findByFilters(List<String> marcas, String genero, String tipo, String talla, Double min,
            Double max, Pageable pageable) {
        return zapatillaRepository.findByFilters(marcas, genero, tipo, talla, min, max, pageable);
    }
}
