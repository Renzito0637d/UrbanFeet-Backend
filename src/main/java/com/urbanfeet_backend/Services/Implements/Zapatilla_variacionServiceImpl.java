package com.urbanfeet_backend.Services.Implements;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.Zapatilla_variacionDAO;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionRequest;
import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionResponse;
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
    @Transactional
    public VariacionResponse crearVariacion(Integer zapatillaId, VariacionRequest request) {
        Zapatilla zap = zapatillaDao.findById(zapatillaId); // Asegúrate que tu DAO devuelva null si no existe o
                                                            // Optional
        if (zap == null) {
            throw new RuntimeException("La zapatilla con ID " + zapatillaId + " no existe.");
        }

        Zapatilla_variacion variacion = new Zapatilla_variacion();
        variacion.setColor(request.color());
        variacion.setTalla(request.talla());
        variacion.setPrecio(request.precio());
        variacion.setStock(request.stock());
        variacion.setImageUrl(request.imageUrl());
        variacion.setZapatilla(zap); // Relación

        Zapatilla_variacion guardada = variacionDao.save(variacion);
        return mapToDto(guardada);
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
    @Transactional
    public VariacionResponse actualizar(Integer id, VariacionRequest request) {
        Zapatilla_variacion v = variacionDao.findById(id);
        if (v == null) throw new RuntimeException("Variación no encontrada");

        v.setColor(request.color());
        v.setTalla(request.talla());
        v.setPrecio(request.precio());
        v.setStock(request.stock());
        v.setImageUrl(request.imageUrl());
        // No cambiamos la zapatilla padre al editar la variación

        Zapatilla_variacion actualizada = variacionDao.update(id, v); // O save() según tu DAO
        return mapToDto(actualizada);
    }

    @Override
    public void eliminarPorId(Integer id) {
        variacionDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true) // <--- CRÍTICO: Mantiene la sesión abierta para leer
    public List<VariacionResponse> findByZapatillaId(Integer zapatillaId) {
        List<Zapatilla_variacion> lista = variacionDao.findByZapatillaId(zapatillaId);
        return lista.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private VariacionResponse mapToDto(Zapatilla_variacion v) {
        return new VariacionResponse(
            v.getId(),
            v.getColor(),
            v.getImageUrl(),
            v.getPrecio(),
            v.getStock(),
            v.getTalla(),
            v.getZapatilla().getId()
        );
    }
}
