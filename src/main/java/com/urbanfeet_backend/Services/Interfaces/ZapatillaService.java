package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

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

    Page<ZapatillaResponse> filtrarCatalogo(List<String> marcas, String genero, String tipo, String talla, Double min,
            Double max, Pageable pageable);
}
