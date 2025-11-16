package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.Zapatilla_variacionDAO;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Services.Interfaces.Zapatilla_variacionService;

@Service
public class Zapatilla_variacionServiceImpl implements Zapatilla_variacionService {
    
    @Autowired
    private Zapatilla_variacionDAO variacionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Zapatilla_variacion> obtenerTodo() {
        return variacionDao.findAll();
    }

    @Override
    @Transactional
    public Zapatilla_variacion guardar(Zapatilla_variacion variacion) {
        return variacionDao.save(variacion);  // retorna entidad
    }

    @Override
    @Transactional(readOnly = true)
    public Zapatilla_variacion obtenerPorId(Integer id) {
        return variacionDao.findById(id);
    }

    @Override
    @Transactional
    public Zapatilla_variacion actualizar(Integer id, Zapatilla_variacion variacion) {
        return variacionDao.update(id, variacion);
    }

    @Override
    @Transactional
    public void eliminarPorId(Integer id) {
        variacionDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Zapatilla_variacion> findByZapatillaId(Integer zapatillaId) {
        return variacionDao.findByZapatillaId(zapatillaId);
    }
}
