package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Zapatilla;

public interface ZapatillaService {

    List<Zapatilla> obtenerTodo();

    Zapatilla guardar(Zapatilla zapatilla);  // <-- ahora retorna

    Zapatilla obtenerPorId(Integer id);      // <-- nombre corregido

    void actualizar(Zapatilla zapatilla);

    void eliminarPorId(Integer id);
}
