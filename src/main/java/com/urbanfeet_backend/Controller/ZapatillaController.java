package com.urbanfeet_backend.Controller;

import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Model.ZapatillaDTOs.ZapatillaResponse;
import com.urbanfeet_backend.Services.Interfaces.ZapatillaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/page")
    public ResponseEntity<Page<ZapatillaResponse>> listarTodoAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        // Configurar dirección de ordenamiento
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // Crear objeto Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Llamar al servicio
        return ResponseEntity.ok(zapatillaService.obtenerPagina(pageable));
    }

    @GetMapping("/public/list")
    public ResponseEntity<Page<ZapatillaResponse>> listarCatalogoPublico(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,

            @RequestParam(required = false) List<String> marcas, 
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String talla,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max
    ) {
        
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(zapatillaService.filtrarCatalogo(
                marcas,
                genero,
                tipo,
                talla,
                min,
                max,
                pageable));
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
