package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.DireccionDAO;
import com.urbanfeet_backend.Entity.Direccion;
import com.urbanfeet_backend.Services.Interfaces.DireccionService;

@Service
public class DireccionServiceImpl implements DireccionService {

    @Autowired
    private DireccionDAO direccionDao;

    @Override
    public List<Direccion> obtenerTodo() {
        return direccionDao.findAll();
    }

    @Override
    public void guardar(Direccion direccion) {
        direccionDao.save(direccion);
    }

    @Override
    public Direccion buscarPorId(Integer id) {
        return direccionDao.findById(id);
    }

    @Override
    public void actualizar(Direccion direccion) {
        direccionDao.update(direccion);
    }

    @Override
    public void eliminarPorId(Integer id) {
        direccionDao.deleteById(id);
    }

}