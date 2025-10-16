package com.urbanfeet_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.urbanfeet_backend.Entity.Pedido_detalle;

@Repository
public interface Pedido_detalleRepository extends JpaRepository<Pedido_detalle, Integer> {
}
