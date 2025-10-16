package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.Zapatilla_variacionDAO;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Services.Interfaces.Zapatilla_variacionService;

@Service
public class Zapatilla_variacionServiceImpl implements Zapatilla_variacionService {

    
    @Autowired
    private Zapatilla_variacionDAO zapatilla_variacionDao;

    @Override
    public List<Zapatilla_variacion> obtenerTodo() {
        return zapatilla_variacionDao.findAll();
    }

    @Override
    public void guardar(Zapatilla_variacion zapatilla_variacion) {
        zapatilla_variacionDao.save(zapatilla_variacion);
    }

    @Override
    public Zapatilla_variacion buscarPorId(Integer id) {
        return zapatilla_variacionDao.findById(id);
    }

    @Override
    public void actualizar(Zapatilla_variacion zapatilla_variacion) {
        zapatilla_variacionDao.update(zapatilla_variacion);
    }

    @Override
    public void eliminarPorId(Integer id) {
        zapatilla_variacionDao.deleteById(id);
    }

}