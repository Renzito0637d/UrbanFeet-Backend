package com.urbanfeet_backend.Controller;

import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Services.Interfaces.ZapatillaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zapatilla")
public class ZapatillaController {

    @Autowired
    private ZapatillaService zapatillaService;

    public record ZapatillaRequest(
            String nombre,
            String descripcion,
            String marca,
            String genero,
            String tipo
    ) {}

   @PostMapping
public ResponseEntity<?> crearZapatilla(@Valid @RequestBody ZapatillaRequest dto) {
    Zapatilla creada = zapatillaService.crearZapatilla(dto);
    return ResponseEntity.ok(creada);
}

@GetMapping("/{id}")
public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
    Zapatilla zap = zapatillaService.obtenerZapatillaPorId(id);
    if (zap == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(zap);
}
}

