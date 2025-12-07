package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.stereotype.Service;

import com.urbanfeet_backend.Entity.Direccion;
import com.urbanfeet_backend.Entity.Direccion_envio;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Repository.DireccionRepository;
import com.urbanfeet_backend.Services.Interfaces.DireccionService;

@Service
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository direccionRepository;

    public DireccionServiceImpl(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    @Override
    public List<Direccion> obtenerTodo() {
        return direccionRepository.findAll();
    }

    @Override
    public void guardar(Direccion direccion) {
        direccionRepository.save(direccion);
    }

    @Override
    public Direccion buscarPorId(Integer id) {
        return direccionRepository.findById(id).orElse(null);
    }

    @Override
    public void actualizar(Direccion direccion) {
        direccionRepository.save(direccion);
    }

    @Override
    public void eliminarPorId(Integer id) {
        direccionRepository.deleteById(id);
    }

    @Override
    public List<Direccion> buscarPorUsuarioId(Integer userId) {
        return direccionRepository.findByUser_Id(userId);
    }

    // -------------------- Lógica de negocio --------------------

    public Direccion crearDireccion(Direccion direccion, User user) {
        direccion.setUser(user);
        guardar(direccion);
        return direccion;
    }

    public Direccion actualizarDireccion(Integer id, Direccion data, User user) {
        Direccion exist = buscarPorId(id);
        if (exist == null)
            return null;
        if (!exist.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No autorizado");
        }

        exist.setCalle(data.getCalle());
        exist.setDistrito(data.getDistrito());
        exist.setProvincia(data.getProvincia());
        exist.setDepartamento(data.getDepartamento());
        exist.setReferencia(data.getReferencia());

        actualizar(exist);
        return exist;
    }

    public boolean eliminarDireccion(Integer id, User user) {
        Direccion exist = buscarPorId(id);
        if (exist == null)
            return false;
        if (!exist.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No autorizado");
        }

        eliminarPorId(id);
        return true;
    }

    public Direccion_envio obtenerPrimeraDireccionEnvio(User user) {
        List<Direccion> direcciones = buscarPorUsuarioId(user.getId());
        Direccion primera = direcciones.stream().findFirst().orElse(null);
        if (primera == null)
            return null;

        return new Direccion_envio(
                primera.getCalle(),
                primera.getDistrito(),
                primera.getProvincia(),
                primera.getDepartamento(),
                primera.getReferencia());
    }
}
