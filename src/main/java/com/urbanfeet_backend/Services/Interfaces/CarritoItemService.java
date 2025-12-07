package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;
import com.urbanfeet_backend.Model.CarritoItemRequest;

public interface CarritoItemService {
    
    public List<Carrito_item> obtenerTodo();

    public void guardar(Carrito_item carrito_item);

    public Carrito_item buscarPorId(Integer id);

    public void actualizar(Carrito_item carrito_item);

    public void eliminarPorId(Integer id);
    
    Carrito buscarCarritoPorId(Integer id);

    Zapatilla_variacion buscarVariacionPorId(Integer id);

    Carrito obtenerCarritoDelUsuario(Authentication auth);

    Carrito_item crearOIncrementarDesdeRequest(CarritoItemRequest request, Authentication auth);

    Carrito_item modificarCantidad(Integer id, boolean incrementar);
}
