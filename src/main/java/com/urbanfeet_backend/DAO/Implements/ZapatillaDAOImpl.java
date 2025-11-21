package com.urbanfeet_backend.DAO.Implements;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.ZapatillaDAO;
import com.urbanfeet_backend.Entity.Zapatilla;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class ZapatillaDAOImpl implements ZapatillaDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Zapatilla> findAll() {
        return em.createQuery("SELECT z FROM Zapatilla z", Zapatilla.class)
                 .getResultList();
    }

    @Override
    public Zapatilla save(Zapatilla zapatilla) {
        em.persist(zapatilla);
        return zapatilla;  // retorna la entidad ya con ID
    }

    @Override
    public Zapatilla findById(Integer id) {
        return em.find(Zapatilla.class, id);
    }

    @Override
    public void update(Zapatilla zapatilla) {
        em.merge(zapatilla);
    }

    @Override
    public void deleteById(Integer id) {
        Zapatilla z = findById(id);
        if (z != null) {
            em.remove(z);
        }
    }
}
