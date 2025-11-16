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

    // DTO para creación
    public record ZapatillaRequest(
            String nombre,
            String descripcion,
            String marca,
            String genero,
            String tipo
    ) {}

    @PostMapping
    public ResponseEntity<?> crearZapatilla(@Valid @RequestBody ZapatillaRequest dto) {

        Zapatilla nueva = new Zapatilla();
        nueva.setNombre(dto.nombre());
        nueva.setDescripcion(dto.descripcion());
        nueva.setMarca(dto.marca());
        nueva.setGenero(dto.genero());
        nueva.setTipo(dto.tipo());

        Zapatilla creada = zapatillaService.guardar(nueva);

        return ResponseEntity.ok(creada);
    }

    @GetMapping
    public List<Zapatilla> listar() {
        return zapatillaService.obtenerTodo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Zapatilla zap = zapatillaService.obtenerPorId(id);
        if (zap == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(zap);
    }
}
