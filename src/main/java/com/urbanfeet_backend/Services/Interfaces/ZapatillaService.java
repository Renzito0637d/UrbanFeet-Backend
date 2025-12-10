package com.urbanfeet_backend.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Model.ZapatillaDTOs.ZapatillaResponse;
import com.urbanfeet_backend.Controller.ZapatillaController.ZapatillaRequest;

public interface ZapatillaService {

    Zapatilla crearZapatilla(ZapatillaRequest dto);

    ZapatillaResponse obtenerZapatillaPorId(Integer id);

    Page<ZapatillaResponse> obtenerPagina(Pageable pageable);

    Page<ZapatillaResponse> obtenerCatalogoPublico(Pageable pageable);

    Zapatilla guardar(Zapatilla zapatilla);

    Zapatilla obtenerPorId(Integer id);

    void eliminarPorId(Integer id);

    Zapatilla actualizarZapatilla(Integer id, ZapatillaRequest request);
}
