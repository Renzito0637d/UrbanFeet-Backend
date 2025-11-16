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
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Model.CarritoItemRequest;
import com.urbanfeet_backend.Services.Interfaces.CarritoItemService;

@RestController
@RequestMapping("/carrito-items")
public class CarritoItemController {

    @Autowired
    private CarritoItemService carritoItemService;

    // Obtener todos los items del carrito
    /*
    @GetMapping
    public ResponseEntity<List<Carrito_item>> getAll() {
        return ResponseEntity.ok(carritoItemService.obtenerTodo());
    }*/

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
        
        Carrito carrito = carritoItemService.buscarCarritoPorId(request.getCarritoId());
        if (carrito == null) {
            return ResponseEntity.badRequest().body("El carrito con ID " + request.getCarritoId() + " no existe");
        }
        
        Zapatilla_variacion variacion = carritoItemService.buscarVariacionPorId(request.getZapatillaVariacionId());
        if (variacion == null) {
            return ResponseEntity.badRequest().body("La variación de zapatilla con ID " + request.getZapatillaVariacionId() + " no existe");
        }

        Carrito_item nuevo = new Carrito_item();
        nuevo.setCarrito(carrito);
        nuevo.setZapatilla_variacion(variacion);
        nuevo.setCantidad(request.getCantidad());
        carritoItemService.guardar(nuevo);
        return ResponseEntity.ok(nuevo);
    }

    // Actualizar item
    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable Integer id,
        @RequestBody CarritoItemRequest request) {
            
            Carrito_item existente = carritoItemService.buscarPorId(id);
            if (existente == null) {
                return ResponseEntity.notFound().build();
            }
            
            Carrito carrito = carritoItemService.buscarCarritoPorId(request.getCarritoId());
            if (carrito == null) {
                return ResponseEntity.badRequest().body("El carrito con ID " + request.getCarritoId() + " no existe");
            }

            Zapatilla_variacion variacion = carritoItemService.buscarVariacionPorId(request.getZapatillaVariacionId());
            if (variacion == null) {
                return ResponseEntity.badRequest().body("La variación de zapatilla con ID " + request.getZapatillaVariacionId() + " no existe");
            }
            
            existente.setCarrito(carrito);
            existente.setZapatilla_variacion(variacion);
            existente.setCantidad(request.getCantidad());
            carritoItemService.guardar(existente);
            return ResponseEntity.ok(existente);
        }
         */

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

