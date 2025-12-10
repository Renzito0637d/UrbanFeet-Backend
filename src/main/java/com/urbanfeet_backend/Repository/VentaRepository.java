package com.urbanfeet_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.urbanfeet_backend.Entity.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    Venta findByPedidoId(Integer pedidoId);
}
