package com.urbanfeet_backend.Services.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.CarritoItemDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Model.CarritoItemRequest;
import com.urbanfeet_backend.Repository.CarritoItemRepository;
import com.urbanfeet_backend.Repository.CarritoRepository;
import com.urbanfeet_backend.Repository.UserRepository;
import com.urbanfeet_backend.Repository.Zapatilla_variacionRepository;
import com.urbanfeet_backend.Services.Interfaces.CarritoItemService;

@Service
public class CarritoItemServiceImpl implements CarritoItemService {

    @Autowired
    private CarritoItemDAO carritoItemDao;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private Zapatilla_variacionRepository variacionRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Carrito_item> obtenerTodo() {
        return carritoItemDao.findAll();
    }

    @Override
    public void guardar(Carrito_item carrito_item) {
        carritoItemDao.save(carrito_item);
    }

    @Override
    public Carrito_item buscarPorId(Integer id) {
        return carritoItemDao.findById(id);
    }

    @Override
    public void actualizar(Carrito_item carrito_item) {
        carritoItemDao.update(carrito_item);
    }

    @Override
    public void eliminarPorId(Integer id) {
        carritoItemDao.deleteById(id);
    }

    @Override
    public Carrito buscarCarritoPorId(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }
    
    @Override
    public Zapatilla_variacion buscarVariacionPorId(Integer id) {
        return variacionRepository.findById(id).orElse(null);
    }
    
    @Override
    public Carrito obtenerCarritoDelUsuario(Authentication auth) {
        if (auth == null) throw new IllegalArgumentException("No autenticado");

        Object principal = auth.getPrincipal();
        String email = (principal instanceof UserDetails ud) ? ud.getUsername() : principal.toString();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));

        return carritoRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no tiene un carrito asociado"));
    }


    @Override
    public Carrito_item crearOIncrementarDesdeRequest(CarritoItemRequest request, Authentication auth) {
        if (request == null) throw new IllegalArgumentException("Request inválido");

        Carrito carrito = obtenerCarritoDelUsuario(auth);

        Zapatilla_variacion variacion = buscarVariacionPorId(request.getZapatillaVariacionId());
        if (variacion == null) throw new IllegalArgumentException("La variación con ID " + request.getZapatillaVariacionId() + " no existe");


        Optional<Carrito_item> existenteOpt = carritoItemRepository.findByCarritoAndZapatillaVariacion(carrito, variacion);

        if (existenteOpt.isPresent()) {
            Carrito_item existente = existenteOpt.get();
            int nuevaCantidad = (existente.getCantidad() == null ? 0 : existente.getCantidad()) + 1;
            existente.setCantidad(nuevaCantidad);
            actualizar(existente);
            return existente;
        } else {
            Carrito_item nuevo = new Carrito_item();
            nuevo.setCarrito(carrito);
            nuevo.setZapatilla_variacion(variacion);
            nuevo.setCantidad(request.getCantidad() == null ? 1 : request.getCantidad());
            guardar(nuevo);
            return nuevo;
        }
    }

    @Override
    public Carrito_item modificarCantidad(Integer id, boolean incrementar) {
        Carrito_item item = buscarPorId(id);
        if (item == null) {
            throw new IllegalArgumentException("Carrito_item no encontrado con id: " + id);
        }

        Integer cantidadActual = item.getCantidad() == null ? 0 : item.getCantidad();
        int nuevaCantidad = cantidadActual + (incrementar ? 1 : -1);

        if (nuevaCantidad <= 0) {
            // Si llega a 0 o menos, eliminamos el item
            eliminarPorId(id);
            return null;
        } else {
            item.setCantidad(nuevaCantidad);
            actualizar(item);
            return item;
        }
    }
}
