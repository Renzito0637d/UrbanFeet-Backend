package com.urbanfeet_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanfeet_backend.Model.VentaDTOs.VentaResponseDTO;
import com.urbanfeet_backend.Services.Interfaces.VentaService;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/all")
    public ResponseEntity<List<VentaResponseDTO>> listarTodasLasVentas() {
        // Ahora llamamos al método que devuelve DTOs
        return ResponseEntity.ok(ventaService.obtenerTodasLasVentasDTO());
    }
}
