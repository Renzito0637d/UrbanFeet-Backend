package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.Zapatilla_variacionDAO;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Services.Interfaces.Zapatilla_variacionService;
import com.urbanfeet_backend.DAO.Interfaces.ZapatillaDAO;
import com.urbanfeet_backend.Entity.Zapatilla;


@Service
public class Zapatilla_variacionServiceImpl implements Zapatilla_variacionService {

    @Autowired
    private Zapatilla_variacionDAO variacionDao;

    @Autowired
    private ZapatillaDAO zapatillaDao;

    @Override
    public Zapatilla_variacion crearVariacion(Integer zapatillaId, Zapatilla_variacion variacion) {

        // 1. Validar existencia de la zapatilla padre
        Zapatilla zap = zapatillaDao.findById(zapatillaId);
        if (zap == null) {
            throw new RuntimeException("La zapatilla con ID " + zapatillaId + " no existe.");
        }

        // 2. Asignar relación
        variacion.setZapatilla(zap);

        // 3. Guardar
        return variacionDao.save(variacion);
    }

    @Override
    public List<Zapatilla_variacion> obtenerTodo() {
        return variacionDao.findAll();
    }

    @Override
    public Zapatilla_variacion guardar(Zapatilla_variacion variacion) {
        return variacionDao.save(variacion);
    }

    @Override
    public Zapatilla_variacion buscarPorId(Integer id) {
        return variacionDao.findById(id);
    }

    @Override
    public Zapatilla_variacion actualizar(Integer id, Zapatilla_variacion variacion) {
        return variacionDao.update(id, variacion);
    }

    @Override
    public void eliminarPorId(Integer id) {
        variacionDao.deleteById(id);
    }

    @Override
    public List<Zapatilla_variacion> findByZapatillaId(Integer zapatillaId) {
        return variacionDao.findByZapatillaId(zapatillaId);
    }
}
