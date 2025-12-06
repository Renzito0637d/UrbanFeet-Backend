package com.urbanfeet_backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.Entity.Carrito;
import com.urbanfeet_backend.Entity.Carrito_item;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;

@Repository
public interface CarritoItemRepository extends JpaRepository<Carrito_item, Integer> {
    List<Carrito_item> findByCarrito(Carrito carrito);
    Optional<Carrito_item> findByCarritoAndZapatilla_variacion(Carrito carrito, Zapatilla_variacion variacion);

}
