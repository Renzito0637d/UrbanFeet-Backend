package com.urbanfeet_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Services.Interfaces.Zapatilla_variacionService;

@RestController
@RequestMapping("/zapatilla-variacion")
public class ZapatillaVariacionController {

    @Autowired
    private Zapatilla_variacionService variacionService;

    @PostMapping("/zapatillas/{zapatillaId}/variaciones")
    public Zapatilla_variacion crearVariacion(
            @PathVariable Integer zapatillaId,
            @RequestBody Zapatilla_variacion variacion) {

        return variacionService.crearVariacion(zapatillaId, variacion);
    }

    @GetMapping("/zapatillas/{zapatillaId}/variaciones")
    public List<Zapatilla_variacion> obtenerVariaciones(@PathVariable Integer zapatillaId) {
        return variacionService.findByZapatillaId(zapatillaId);
    }
}
