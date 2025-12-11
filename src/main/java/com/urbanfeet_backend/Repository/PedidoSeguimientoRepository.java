package com.urbanfeet_backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.Entity.PedidoSeguimiento;

@Repository
public interface PedidoSeguimientoRepository extends JpaRepository<PedidoSeguimiento, Integer> {
    List<PedidoSeguimiento> findByPedidoIdOrderByFechaCambioDesc(Integer pedidoId);
}