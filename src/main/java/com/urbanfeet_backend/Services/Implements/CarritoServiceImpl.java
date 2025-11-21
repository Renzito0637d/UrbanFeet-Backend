package com.urbanfeet_backend.Services.Implements;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.CarritoDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Services.Interfaces.CarritoService;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoDAO carritoDao;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Carrito> obtenerTodo() {
        return carritoDao.findAll();
    }
    
    @Override
    public Carrito guardar(Carrito carrito) {
        if (carrito == null) {
            throw new IllegalArgumentException("Carrito no puede ser null");
        }

        if (carrito.getUser() == null || carrito.getUser().getId() == null) {
            throw new IllegalArgumentException("Debe enviar el id del usuario.");
        }

        Integer userId = carrito.getUser().getId();
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new IllegalArgumentException("El usuario con id " + userId + " no existe.");
        }

        // Setear la entidad completa (asegura que JPA relacione correctamente)
        carrito.setUser(optUser.get());
        return carritoDao.save(carrito);
    }

    @Override
    public Carrito buscarPorId(Integer id) {
        return carritoDao.findById(id);
    }

    @Override
    public Carrito actualizar(Carrito carrito) {
        if (carrito == null || carrito.getId() == null) {
            throw new IllegalArgumentException("Carrito o id inválido para actualizar.");
        }

        // validar existencia del carrito a actualizar
        Carrito existente = carritoDao.findById(carrito.getId());
        if (existente == null) {
            throw new IllegalArgumentException("El carrito con id " + carrito.getId() + " no existe.");
        }

        // validar user si se envía
        if (carrito.getUser() != null && carrito.getUser().getId() != null) {
            Integer userId = carrito.getUser().getId();
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new IllegalArgumentException("El usuario con id " + userId + " no existe."));
            carrito.setUser(user);
        } else {
            // mantener el user existente si no se proporcionó
            carrito.setUser(existente.getUser());
        }

        return carritoDao.update(carrito);
    }

    @Override
    public void eliminarPorId(Integer id) {
        carritoDao.deleteById(id);
    }
}