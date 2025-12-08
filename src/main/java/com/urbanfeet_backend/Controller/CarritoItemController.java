package com.urbanfeet_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Model.CarritoItemRequest;
import com.urbanfeet_backend.Model.CarritoItemResponse;
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
    public ResponseEntity<?> createOrIncrement(Authentication authentication,
            @RequestBody CarritoItemRequest request) {
        try {
            // El servicio ahora devuelve un CarritoItemResponse, no una entidad
            CarritoItemResponse resultado = carritoItemService.crearOIncrementarDesdeRequest(request, authentication);

            // Jackson serializará este DTO sin problemas porque no tiene enlaces Lazy
            return ResponseEntity.ok(resultado);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error interno: " + ex.getMessage());
        }
    }

    // Eliminar item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, Authentication authentication) {
        try {
            // Llamamos al método seguro del servicio
            carritoItemService.eliminarPorId(id, authentication);

            // 204 No Content es el estándar para un borrado exitoso
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException ex) {
            // 404 Not Found si el ID no existe
            return ResponseEntity.notFound().build();

        } catch (SecurityException ex) {
            // 403 Forbidden si intenta borrar algo de otro usuario
            return ResponseEntity.status(403).body(ex.getMessage());

        } catch (Exception ex) {
            // 500 para cualquier otro error
            return ResponseEntity.status(500).body("Error al eliminar: " + ex.getMessage());
        }
    }

    // Modificar cantidad de item
    @PatchMapping("/{itemId}/cantidad")
    public ResponseEntity<?> cambiarCantidad(
            @PathVariable Integer itemId,
            @RequestParam("op") String operacion,
            Authentication authentication) {

        boolean incrementar = "increment".equalsIgnoreCase(operacion) || "plus".equalsIgnoreCase(operacion)
                || "+".equals(operacion);

        try {
            // 2. Pasamos authentication al servicio
            Carrito_item item = carritoItemService.modificarCantidad(itemId, incrementar, authentication);

            if (item == null) {
                return ResponseEntity.noContent().build();
            }

            CarritoItemResponse response = new CarritoItemResponse(
                    item.getId(),
                    item.getCantidad(),
                    item.getZapatilla_variacion().getId(),
                    item.getZapatilla_variacion().getPrecio());

            return ResponseEntity.ok(response);

        } catch (SecurityException ex) {
            // 3. IMPORTANTE: Devolvemos 403 Forbidden si intenta tocar lo que no es suyo
            return ResponseEntity.status(403).body("Acceso denegado: " + ex.getMessage());

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error interno: " + ex.getMessage());
        }
    }
}
