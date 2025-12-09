package com.urbanfeet_backend.Controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.urbanfeet_backend.Model.SugerenciaDTOs.SugerenciaRequest;
import com.urbanfeet_backend.Model.SugerenciaDTOs.SugerenciaResponse;
import com.urbanfeet_backend.Services.Interfaces.SugerenciaService;

@RestController
@RequestMapping("/sugerencias")
public class SugerenciaController {

    private final SugerenciaService service;

    public SugerenciaController(SugerenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<SugerenciaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public SugerenciaResponse obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public SugerenciaResponse crear(@RequestBody SugerenciaRequest request, Authentication auth) {
        return service.crear(request, auth);
    }

    @PutMapping("/{id}")
    public SugerenciaResponse actualizar(@PathVariable Integer id, @RequestBody SugerenciaRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }

}
