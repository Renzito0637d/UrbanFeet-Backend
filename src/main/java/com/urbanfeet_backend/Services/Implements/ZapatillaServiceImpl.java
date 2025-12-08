package com.urbanfeet_backend.Services.Implements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urbanfeet_backend.DAO.Interfaces.ZapatillaDAO;
import com.urbanfeet_backend.Entity.Zapatilla;
import com.urbanfeet_backend.Model.ZapatillaDTOs.VariacionResponse;
import com.urbanfeet_backend.Model.ZapatillaDTOs.ZapatillaResponse;
import com.urbanfeet_backend.Services.Interfaces.ZapatillaService;
import com.urbanfeet_backend.Controller.ZapatillaController.ZapatillaRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZapatillaServiceImpl implements ZapatillaService {

    @Autowired
    private ZapatillaDAO zapatillaDao;

    // -----------------------------------------
    // Lógica refactorizada desde el controlador
    // -----------------------------------------
    @Override
    public Zapatilla crearZapatilla(ZapatillaRequest dto) {

        Zapatilla nueva = new Zapatilla();
        nueva.setNombre(dto.nombre());
        nueva.setDescripcion(dto.descripcion());
        nueva.setMarca(dto.marca());
        nueva.setGenero(dto.genero());
        nueva.setTipo(dto.tipo());

        return zapatillaDao.save(nueva);
    }

    @Override
    @Transactional(readOnly = true) // <--- IMPORTANTE: Mantiene la sesión abierta
    public ZapatillaResponse obtenerZapatillaPorId(Integer id) {
        Zapatilla z = zapatillaDao.findById(id);
        
        if (z == null) {
            return null;
        }

        // Convertimos a DTO usando el método helper
        return mapToDto(z);
    }

    // -----------------------------------------
    // Métodos CRUD estándar
    // -----------------------------------------
    @Override
    @Transactional(readOnly = true) // <--- ESTO EVITA EL ERROR LAZY
    public List<ZapatillaResponse> obtenerTodo() {
        List<Zapatilla> zapatillas = zapatillaDao.findAll();

        return zapatillas.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir Entidad -> DTO
    private ZapatillaResponse mapToDto(Zapatilla z) {
        ZapatillaResponse dto = new ZapatillaResponse();
        dto.setId(z.getId());
        dto.setNombre(z.getNombre());
        dto.setDescripcion(z.getDescripcion());
        dto.setMarca(z.getMarca());
        dto.setGenero(z.getGenero());
        dto.setTipo(z.getTipo());

        // Mapeamos las variaciones manualmente
        if (z.getVariaciones() != null) {
            List<VariacionResponse> varDtos = z.getVariaciones().stream()
                    .map(v -> new VariacionResponse(
                            v.getId(),
                            v.getColor(),
                            v.getImageUrl(),
                            v.getPrecio(),
                            v.getStock(),
                            v.getTalla(),
                            v.getZapatilla().getId()

                    ))
                    .collect(Collectors.toList());
            dto.setVariaciones(varDtos);
        }

        return dto;
    }

    @Override
    public Zapatilla guardar(Zapatilla zapatilla) {
        return zapatillaDao.save(zapatilla);
    }

    @Override
    public Zapatilla obtenerPorId(Integer id) {
        return zapatillaDao.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        zapatillaDao.deleteById(id);
    }

    @Override
    public Zapatilla actualizarZapatilla(Integer id, ZapatillaRequest request) {
        Zapatilla zapatilla = zapatillaDao.findById(id);

        zapatilla.setNombre(request.nombre());
        zapatilla.setMarca(request.marca());
        zapatilla.setDescripcion(request.descripcion());
        zapatilla.setGenero(request.genero());
        zapatilla.setTipo(request.tipo());

        return zapatillaDao.update(zapatilla);
    }
}
