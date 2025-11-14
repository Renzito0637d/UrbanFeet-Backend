package com.urbanfeet_backend.Controller;

import com.urbanfeet_backend.DAO.Interfaces.ZapatillaDAO;
import com.urbanfeet_backend.Entity.Zapatilla;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zapatillas") // Ruta base
public class ZapatillaController {

    @Autowired
    private ZapatillaService zapatillaService;

    public record ZapatillaRecord(
            String nombre,
            String descripcion,
            String marca,
            String genero,
            String tipo) {
    }

    @GetMapping("/all")
    public List<ZapatillaRecord> todasLasZapatillas() {

        List<Zapatilla> zapatillasEntidades = zapatillaService.obtenerTodo();

        List<ZapatillaRecord> zapatillasRecords = zapatillasEntidades.stream()
                .map(zapatilla -> new ZapatillaRecord(
                        zapatilla.getNombre(),
                        zapatilla.getDescripcion(),
                        zapatilla.getMarca(),
                        zapatilla.getGenero(),
                        zapatilla.getTipo()))
                .collect(Collectors.toList());

        return zapatillasRecords;
    }

}
