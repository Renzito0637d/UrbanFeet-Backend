package com.urbanfeet_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<?> createOrIncrement(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody CarritoItemRequest request) {

        try {
            Carrito_item resultado = carritoItemService.crearOIncrementarDesdeRequest(request, authHeader);
            return ResponseEntity.ok(resultado);
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

    // Modificar cantidad de item
    @PatchMapping("/{itemId}/cantidad")
    public ResponseEntity<?> cambiarCantidad(
            @PathVariable Integer itemId,
            @RequestParam("op") String operacion) {

        boolean incrementar = "increment".equalsIgnoreCase(operacion) || "plus".equalsIgnoreCase(operacion) || "+".equals(operacion);

        try {
            Carrito_item item = carritoItemService.modificarCantidad(itemId, incrementar);
            if (item == null) {
                // fue eliminado porque la cantidad llegó a 0
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(item);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error interno: " + ex.getMessage());
        }
    }    
}    

