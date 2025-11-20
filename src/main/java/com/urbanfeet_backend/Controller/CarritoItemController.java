package com.urbanfeet_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Model.CarritoItemRequest;
import com.urbanfeet_backend.Services.Interfaces.CarritoItemService;

@RestController
@RequestMapping("/carrito-items")
public class CarritoItemController {

    @Autowired
    private CarritoItemService carritoItemService;

    // Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<Carrito_item> getById(@PathVariable Integer id) {
        Carrito_item ci = carritoItemService.buscarPorId(id);
        if (ci == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ci);
    }

    // Crear item del carrito
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CarritoItemRequest request) {
        try {
            Carrito_item creado = carritoItemService.crearDesdeRequest(request);
            return ResponseEntity.ok(creado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error interno: " + ex.getMessage());
        }
    }

    // Eliminar item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Carrito_item existente = carritoItemService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        carritoItemService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}    

