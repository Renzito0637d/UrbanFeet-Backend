package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.urbanfeet_backend.Model.SugerenciaDTOs.SugerenciaRequest;
import com.urbanfeet_backend.Model.SugerenciaDTOs.SugerenciaResponse;

public interface SugerenciaService {

    public List<SugerenciaResponse> listar();

    public SugerenciaResponse obtenerPorId(Integer id);

    public SugerenciaResponse crear(SugerenciaRequest request, Authentication auth);

    public SugerenciaResponse actualizar(Integer id, SugerenciaRequest request);

    public void eliminar(Integer id);

}
