package com.urbanfeet_backend.Services.Implements;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.ZapatillaDAO;
import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Services.Interfaces.ZapatillaService;

@Service
public class ZapatillaServiceImpl implements ZapatillaService {

    @Autowired
    private ZapatillaDAO zapatillaDao;

    @Override
    public List<Zapatilla> obtenerTodo() {
        return zapatillaDao.findAll();
    }

    @Override
    public Zapatilla guardar(Zapatilla zapatilla) {
        return zapatillaDao.save(zapatilla); // <-- ahora retorna
    }

    @Override
    public Zapatilla obtenerPorId(Integer id) {
        return zapatillaDao.findById(id);    // <-- método renombrado correctamente
    }

    @Override
    public void actualizar(Zapatilla zapatilla) {
        zapatillaDao.update(zapatilla);
    }

    @Override
    public void eliminarPorId(Integer id) {
        zapatillaDao.deleteById(id);
    }
}
