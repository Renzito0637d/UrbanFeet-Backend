package com.urbanfeet_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionRequest;
import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionResponse;
import com.urbanfeet_backend.Services.Interfaces.Zapatilla_variacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/zapatilla-variacion")
public class ZapatillaVariacionController {

    @Autowired
    private Zapatilla_variacionService variacionService;

    @PostMapping("/zapatilla/{zapatillaId}")
    public ResponseEntity<VariacionResponse> crearVariacion(
            @PathVariable Integer zapatillaId,
            @Valid @RequestBody VariacionRequest request) {

        return ResponseEntity.ok(variacionService.crearVariacion(zapatillaId, request));
    }

    // LISTAR POR ZAPATILLA
    @GetMapping("/zapatillas/{zapatillaId}")
    public ResponseEntity<List<VariacionResponse>> obtenerVariaciones(@PathVariable Integer zapatillaId) {
        return ResponseEntity.ok(variacionService.findByZapatillaId(zapatillaId));
    }

    // ACTUALIZAR (Nuevo endpoint)
    @PutMapping("/{id}")
    public ResponseEntity<VariacionResponse> actualizarVariacion(
            @PathVariable Integer id,
            @Valid @RequestBody VariacionRequest request) {

        return ResponseEntity.ok(variacionService.actualizar(id, request));
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVariacion(@PathVariable Integer id) {
        variacionService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
