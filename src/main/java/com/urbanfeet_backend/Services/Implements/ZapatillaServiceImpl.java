package com.urbanfeet_backend.Services.Implements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.ZapatillaDAO;
import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Services.Interfaces.ZapatillaService;
import com.urbanfeet_backend.Controller.ZapatillaController.ZapatillaRequest;

import java.util.List;

@Service
public class ZapatillaServiceImpl implements ZapatillaService {

    @Autowired
    private ZapatillaDAO zapatillaDao;

    // -----------------------------------------
    // Lógica refactorizada desde el controlador
    // -----------------------------------------
    @Override
    public Zapatilla crearZapatilla(ZapatillaRequest dto) {

        Zapatilla nueva = new Zapatilla();
        nueva.setNombre(dto.nombre());
        nueva.setDescripcion(dto.descripcion());
        nueva.setMarca(dto.marca());
        nueva.setGenero(dto.genero());
        nueva.setTipo(dto.tipo());

        return zapatillaDao.save(nueva); 
    }

    @Override
    public Zapatilla obtenerZapatillaPorId(Integer id) {
        return zapatillaDao.findById(id);
    }

    // -----------------------------------------
    // Métodos CRUD estándar
    // -----------------------------------------
    @Override
    public List<Zapatilla> obtenerTodo() {
        return zapatillaDao.findAll();
    }

    @Override
    public Zapatilla guardar(Zapatilla zapatilla) {
        return zapatillaDao.save(zapatilla);
    }

    @Override
    public Zapatilla obtenerPorId(Integer id) {
        return zapatillaDao.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        zapatillaDao.deleteById(id);
    }
}
