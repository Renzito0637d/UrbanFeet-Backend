package com.urbanfeet_backend.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Services.Interfaces.ZapatillaService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/zapatilla")
public class ZapatillaController {

        @Autowired
        private ZapatillaService zapatillaService;
        
        // REFACTORIZAR EN SERIVIOS
        // ---------------------------------------------------------

        // public record ZapatillaRecord(
        // String nombre,
        // String descripcion,
        // String marca,
        // String genero,
        // String tipo) {
        // }

        // @GetMapping("/all")
        // public List<ZapatillaRecord> todasLasZapatillas() {

        // List<Zapatilla> zapatillasEntidades = zapatillaService.obtenerTodo();

        // List<ZapatillaRecord> zapatillasRecords = zapatillasEntidades.stream()
        // .map(zapatilla -> new ZapatillaRecord(
        // zapatilla.getNombre(),
        // zapatilla.getDescripcion(),
        // zapatilla.getMarca(),
        // zapatilla.getGenero(),
        // zapatilla.getTipo()))
        // .collect(Collectors.toList());

        // return zapatillasRecords;
        // }

}
