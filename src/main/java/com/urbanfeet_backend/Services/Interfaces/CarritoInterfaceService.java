package com.urbanfeet_backend.Services.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.Carrito_item;

public interface CarritoItemService {
    List<Carrito_item> getItemsByCarrito(Carrito carrito);
    Carrito_item saveItem(Carrito_item item);
    void deleteItem(Carrito_item item);
}
