package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Añadido

import com.urbanfeet_backend.DAO.Interfaces.Zapatilla_variacionDAO;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Services.Interfaces.Zapatilla_variacionService;

@Service
public class Zapatilla_variacionServiceImpl implements Zapatilla_variacionService {
    
    @Autowired
    private Zapatilla_variacionDAO zapatilla_variacionDao;

    @Override
    @Transactional(readOnly = true) 
    public List<Zapatilla_variacion> obtenerTodo() {
        return zapatilla_variacionDao.findAll();
    }

    @Override
    @Transactional 
    public void guardar(Zapatilla_variacion zapatilla_variacion) {
        // La lógica de asignar el padre está en el DAO o en el Controller
        zapatilla_variacionDao.save(zapatilla_variacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Zapatilla_variacion buscarPorId(Integer id) {
        // El DAO se encarga de lanzar la excepción si no lo encuentra
        return zapatilla_variacionDao.findById(id);
    }

    @Override
    @Transactional
    public Zapatilla_variacion actualizar(Integer id, Zapatilla_variacion zapatilla_variacion) {
        // Pasamos la lógica de actualización al DAO
        return zapatilla_variacionDao.update(id, zapatilla_variacion);
    }

    @Override
    @Transactional
    public void eliminarPorId(Integer id) {
        zapatilla_variacionDao.deleteById(id);
    }

    // --- MÉTODO NUEVO ---
    @Override
    @Transactional(readOnly = true)
    public List<Zapatilla_variacion> findByZapatillaId(Integer zapatillaId) {
        // El DAO se encarga de verificar si la zapatilla existe
        return zapatilla_variacionDao.findByZapatillaId(zapatillaId);
    }
}