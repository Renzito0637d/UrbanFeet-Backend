package com.urbanfeet_backend.Services.Implements;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.ReclamacionDAO;
import com.urbanfeet_backend.Entity.Reclamacion;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Services.Interfaces.ReclamacionService;

@Service
public class ReclamacionServiceImpl implements ReclamacionService {

    private final ReclamacionDAO reclamacionDAO;
    private final UserRepository userRepository;

    public ReclamacionServiceImpl(ReclamacionDAO reclamacionDAO, UserRepository userRepository) {
        this.reclamacionDAO = reclamacionDAO;
        this.userRepository = userRepository;
    }

    @Override
    public Reclamacion crearReclamacion(Reclamacion r, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        r.setUser(user);
        r.setFechaRegistro(LocalDateTime.now());
        r.setEstado("PENDIENTE");

        reclamacionDAO.save(r);
        return r;
    }

    @Override
    public List<Reclamacion> obtenerMisReclamaciones(Integer userId) {
        return reclamacionDAO.findByUserId(userId);
    }

    @Override
    public List<Reclamacion> obtenerTodos() {
        return reclamacionDAO.findAll();
    }

    @Override
    public Reclamacion obtenerPorId(Integer id) {
        Reclamacion r = reclamacionDAO.findById(id);
        if (r == null)
            throw new RuntimeException("Reclamacion no encontrada");
        return r;
    }

    @Override
    public Reclamacion actualizar(Integer id, Reclamacion data) {
        Reclamacion exist = reclamacionDAO.findById(id);
        if (exist == null)
            throw new RuntimeException("Reclamacion no encontrada");

        exist.setProducto(data.getProducto());
        exist.setMontoReclamado(data.getMontoReclamado());
        exist.setTipoMensaje(data.getTipoMensaje());
        exist.setDetalleReclamo(data.getDetalleReclamo());
        exist.setSolucionPropuesta(data.getSolucionPropuesta());

        if (data.getEstado() != null) {
            exist.setEstado(data.getEstado());
        }

        reclamacionDAO.update(exist);
        return exist;
    }

    @Override
    public void eliminar(Integer id) {
        reclamacionDAO.deleteById(id);
    }
}
