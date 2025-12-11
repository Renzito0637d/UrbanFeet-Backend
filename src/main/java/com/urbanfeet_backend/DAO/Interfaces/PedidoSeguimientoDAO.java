package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;

import com.urbanfeet_backend.Entity.PedidoSeguimiento;

public interface PedidoSeguimientoDAO {

    List<PedidoSeguimiento> findByPedidoIdOrderByFechaCambioDesc(Integer pedidoId);

    public void save(PedidoSeguimiento pedidoSeguimiento);

}
