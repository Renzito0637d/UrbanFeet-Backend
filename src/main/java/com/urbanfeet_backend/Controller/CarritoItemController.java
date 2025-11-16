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

import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Services.Interfaces.CarritoItemService;

@RestController
@RequestMapping("/carrito-items")
public class CarritoItemController {

    @Autowired
    private CarritoItemService carritoItemService;

    // Obtener todos los items del carrito
    @GetMapping
    public ResponseEntity<List<Carrito_item>> getAll() {
        return ResponseEntity.ok(carritoItemService.obtenerTodo());
    }

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
    public ResponseEntity<Carrito_item> create(@RequestBody Carrito_item carrito_item) {
        carritoItemService.guardar(carrito_item);
        return ResponseEntity.ok(carrito_item);
    }

    // Actualizar item
    @PutMapping("/{id}")
    public ResponseEntity<Carrito_item> update(@PathVariable Integer id, @RequestBody Carrito_item carrito_item) {
        Carrito_item existente = carritoItemService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        carrito_item.setId(id);
        carritoItemService.actualizar(carrito_item);
        return ResponseEntity.ok(carrito_item);
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

