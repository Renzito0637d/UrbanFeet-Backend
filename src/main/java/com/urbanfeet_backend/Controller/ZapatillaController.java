package com.urbanfeet_backend.Controller;

import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Model.ZapatillaDTOs.ZapatillaResponse;
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
            String tipo) {
    }

    @PostMapping
    public ResponseEntity<?> crearZapatilla(@Valid @RequestBody ZapatillaRequest dto) {
        Zapatilla creada = zapatillaService.crearZapatilla(dto);
        return ResponseEntity.ok(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZapatillaResponse> obtenerPorId(@PathVariable Integer id) {
        ZapatillaResponse zapDto = zapatillaService.obtenerZapatillaPorId(id);

        if (zapDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(zapDto);
    }

    @GetMapping
    public ResponseEntity<List<ZapatillaResponse>> listarTodas() {
        return ResponseEntity.ok(zapatillaService.obtenerTodo());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarZapatilla(@PathVariable Integer id, @Valid @RequestBody ZapatillaRequest dto) {
        Zapatilla actualizada = zapatillaService.actualizarZapatilla(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarZapatilla(@PathVariable Integer id) {
        // Asumo que tu servicio tiene el método deleteZapatilla(id)
        // Gracias al CascadeType.ALL que pusimos en la entidad,
        // al borrar la zapatilla SE BORRARÁN TODAS SUS VARIACIONES automáticamente.
        zapatillaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
