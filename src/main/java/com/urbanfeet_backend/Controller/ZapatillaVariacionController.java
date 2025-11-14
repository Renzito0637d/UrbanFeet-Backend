package com.urbanfeet_backend.Controller;

// --- INYECCIONES CORREGIDAS ---
import com.urbanfeet_backend.Services.Interfaces.ZapatillaService;
import com.urbanfeet_backend.Services.Interfaces.Zapatilla_variacionService;
// ---
import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ZapatillaVariacionController {

    // --- CORRECCIÓN: Inyectamos el Servicio, no el DAO ---
    @Autowired
    private Zapatilla_variacionService variacionService;

    // --- CORRECCIÓN: Inyectamos el Servicio, no el DAO ---
    // (Asegúrate de tener este ZapatillaService con un método buscarPorId)
    @Autowired
    private ZapatillaService zapatillaService; 

    // --- ENDPOINTS PARA VARIACIONES INDIVIDUALES ---

    @GetMapping("/variaciones/{id}")
    public ResponseEntity<Zapatilla_variacion> getVariacionById(@PathVariable Integer id) {
        // --- CORRECCIÓN: Usamos el método del servicio ---
        Zapatilla_variacion variacion = variacionService.buscarPorId(id);
        return ResponseEntity.ok(variacion);
    }

    @PutMapping("/variaciones/{id}")
    public ResponseEntity<Zapatilla_variacion> updateVariacion(@PathVariable Integer id, @Valid @RequestBody Zapatilla_variacion variacionDetails) {
        // --- CORRECCIÓN: Usamos el método del servicio ---
        Zapatilla_variacion variacionActualizada = variacionService.actualizar(id, variacionDetails);
        return ResponseEntity.ok(variacionActualizada);
    }

    @DeleteMapping("/variaciones/{id}")
    public ResponseEntity<Void> deleteVariacion(@PathVariable Integer id) {
        // --- CORRECCIÓN: Usamos el método del servicio ---
        variacionService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    // --- ENDPOINTS PARA VARIACIONES RELACIONADAS A UNA ZAPATILLA ---

    @PostMapping("/zapatillas/{zapatillaId}/variaciones")
    public ResponseEntity<Zapatilla_variacion> createVariacion(
            @PathVariable Integer zapatillaId,
            @Valid @RequestBody Zapatilla_variacion variacion) {
        
        // --- CORRECCIÓN: Usamos el servicio de zapatillas ---
        Zapatilla zapatillaPadre = zapatillaService.buscarPorId(zapatillaId);
        
        variacion.setZapatilla(zapatillaPadre);
        variacion.setId(null); 
        
        // --- CORRECCIÓN: Usamos el método del servicio ---
        variacionService.guardar(variacion);
        
        return new ResponseEntity<>(variacion, HttpStatus.CREATED);
    }

    @GetMapping("/zapatillas/{zapatillaId}/variaciones")
    public ResponseEntity<List<Zapatilla_variacion>> getVariacionesByZapatillaId(@PathVariable Integer zapatillaId) {
        // --- CORRECCIÓN: Usamos el método del servicio ---
        List<Zapatilla_variacion> variaciones = variacionService.findByZapatillaId(zapatillaId);
        return ResponseEntity.ok(variaciones);
    }
}