package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Controller.ZapatillaController.ZapatillaRequest;

public interface ZapatillaService {

    Zapatilla crearZapatilla(ZapatillaRequest dto);

    Zapatilla obtenerZapatillaPorId(Integer id);

    List<Zapatilla> obtenerTodo();

    Zapatilla guardar(Zapatilla zapatilla);

    Zapatilla obtenerPorId(Integer id);

    void eliminarPorId(Integer id);
}
