package com.urbanfeet_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Services.Interfaces.CarritoService;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
    
    @Autowired
    private CarritoService carritoService;

    // Obtener todos los carritos
    @GetMapping
    public ResponseEntity<List<Carrito>> getAll() {
        return ResponseEntity.ok(carritoService.obtenerTodo());
    }
    

    // Obtener carrito por id
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> getById(@PathVariable Integer id) {
        Carrito c = carritoService.buscarPorId(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    // Crear nuevo carrito
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Carrito carrito) {
        try {
            Carrito guardado = carritoService.guardar(carrito);
            return ResponseEntity.ok(guardado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error interno: " + ex.getMessage());
        }
    }

    // Actualizar carrito
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Carrito carrito) {
        try {
            Carrito existente = carritoService.buscarPorId(id);
            if (existente == null) {
                return ResponseEntity.notFound().build();
            }
            carrito.setId(id);
            Carrito actualizado = carritoService.actualizar(carrito);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error interno: " + ex.getMessage());
        }
    }

    // Eliminar carrito
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Carrito existente = carritoService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        carritoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
