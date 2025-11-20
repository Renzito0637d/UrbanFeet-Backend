package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.CarritoItemDAO;
import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Model.CarritoItemRequest;
import com.urbanfeet_backend.Repository.CarritoRepository;
import com.urbanfeet_backend.Repository.Zapatilla_variacionRepository;
import com.urbanfeet_backend.Services.Interfaces.CarritoItemService;

@Service
public class CarritoItemServiceImpl implements CarritoItemService {

    @Autowired
    private CarritoItemDAO carritoItemDao;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private Zapatilla_variacionRepository variacionRepository;

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
    public Carrito_item crearDesdeRequest(CarritoItemRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request inválido");
        }

        Carrito carrito = buscarCarritoPorId(request.getCarritoId());
        if (carrito == null) {
            throw new IllegalArgumentException("El carrito con ID " + request.getCarritoId() + " no existe");
        }

        Zapatilla_variacion variacion = buscarVariacionPorId(request.getZapatillaVariacionId());
        if (variacion == null) {
            throw new IllegalArgumentException(
                    "La variación de zapatilla con ID " + request.getZapatillaVariacionId() + " no existe");
        }

        Carrito_item nuevo = new Carrito_item();
        nuevo.setCarrito(carrito);
        nuevo.setZapatilla_variacion(variacion);
        nuevo.setCantidad(request.getCantidad());

        guardar(nuevo);
        return nuevo;
    }    
    
    @Override
    public Carrito_item actualizarDesdeRequest(Integer id, CarritoItemRequest request) {
        Carrito_item existente = buscarPorId(id);
        if (existente == null) {
            throw new IllegalArgumentException("El item con id " + id + " no existe");
        }

        Carrito carrito = buscarCarritoPorId(request.getCarritoId());
        if (carrito == null) {
            throw new IllegalArgumentException("El carrito con ID " + request.getCarritoId() + " no existe");
        }

        Zapatilla_variacion variacion = buscarVariacionPorId(request.getZapatillaVariacionId());
        if (variacion == null) {
            throw new IllegalArgumentException(
                    "La variación de zapatilla con ID " + request.getZapatillaVariacionId() + " no existe");
        }

        existente.setCarrito(carrito);
        existente.setZapatilla_variacion(variacion);
        existente.setCantidad(request.getCantidad());

        actualizar(existente);
        return existente;
    }
}
