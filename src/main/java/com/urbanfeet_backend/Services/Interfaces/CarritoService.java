package com.urbanfeet_backend.Services.Interfaces;

import java.util.Optional;

import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.User;

public interface CarritoService {
    Optional<Carrito> getCarritoByUser(User user);
    Carrito saveCarrito(Carrito carrito);
    void deleteCarrito(Carrito carrito);
}
