package com.urbanfeet_backend.Services.Implements;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.SugerenciaDAO;
import com.urbanfeet_backend.DAO.Interfaces.UserDAO;
import com.urbanfeet_backend.Entity.Sugerencia;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Model.SugerenciaDTOs.SugerenciaRequest;
import com.urbanfeet_backend.Model.SugerenciaDTOs.SugerenciaResponse;
import com.urbanfeet_backend.Services.Interfaces.SugerenciaService;

@Service
public class SugerenciaServiceImpl implements SugerenciaService {

    private final SugerenciaDAO sugerenciaDAO;
    private final UserDAO userDAO;

    public SugerenciaServiceImpl(SugerenciaDAO sugerenciaDAO, UserDAO userDAO) {
        this.sugerenciaDAO = sugerenciaDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<SugerenciaResponse> listar() {
        return sugerenciaDAO.listarSugerencias()
                .stream()
                .map(s -> new SugerenciaResponse(
                        s.getId(),
                        s.getAsunto(),
                        s.getMensaje(),
                        s.getFechaEnvio(),
                        s.getEstado(),
                        s.getUser().getId()
                ))
                .toList();
    }

    @Override
    public SugerenciaResponse obtenerPorId(Integer id) {
        Sugerencia s = sugerenciaDAO.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Sugerencia no encontrada"));

        return new SugerenciaResponse(
                s.getId(),
                s.getAsunto(),
                s.getMensaje(),
                s.getFechaEnvio(),
                s.getEstado(),
                s.getUser().getId()
        );
    }

    @Override
    public SugerenciaResponse crear(SugerenciaRequest request, Authentication auth) {

        String email = auth.getName();
        User usuario = userDAO.buscarUserPorCorreo(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Sugerencia s = new Sugerencia();
        s.setAsunto(request.getAsunto());
        s.setMensaje(request.getMensaje());
        s.setFechaEnvio(LocalDateTime.now());
        s.setEstado("pendiente");
        s.setUser(usuario);

        sugerenciaDAO.guardar(s);

        return new SugerenciaResponse(
                s.getId(),
                s.getAsunto(),
                s.getMensaje(),
                s.getFechaEnvio(),
                s.getEstado(),
                usuario.getId()
        );
    }

    @Override
    public SugerenciaResponse actualizar(Integer id, SugerenciaRequest request) {
        Sugerencia s = sugerenciaDAO.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Sugerencia no encontrada"));

        s.setAsunto(request.getAsunto());
        s.setMensaje(request.getMensaje());
        sugerenciaDAO.guardar(s);

        return new SugerenciaResponse(
                s.getId(),
                s.getAsunto(),
                s.getMensaje(),
                s.getFechaEnvio(),
                s.getEstado(),
                s.getUser().getId()
        );
    }

    @Override
    public void eliminar(Integer id) {
        sugerenciaDAO.eliminar(id);
    }

}
