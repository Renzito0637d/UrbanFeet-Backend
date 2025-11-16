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
        if (carrito.getUser() == null || carrito.getUser().getId() == null) {
            return ResponseEntity.badRequest().body("Debe enviar el id del usuario.");
        }
        
        Carrito guardado = carritoService.guardar(carrito);
        return ResponseEntity.ok(guardado);
    }

    // Actualizar carrito
    @PutMapping("/{id}")
    public ResponseEntity<Carrito> update(@PathVariable Integer id, @RequestBody Carrito carrito) {
        Carrito existente = carritoService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        // asegurar que el id del body coincida con el path (o forzarlo)
        carrito.setId(id);
        Carrito actualizado = carritoService.actualizar(carrito);
        return ResponseEntity.ok(actualizado);
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
