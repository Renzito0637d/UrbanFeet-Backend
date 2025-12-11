package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.PedidoSeguimientoDAO;
import com.urbanfeet_backend.Entity.PedidoSeguimiento;
import com.urbanfeet_backend.Repository.PedidoSeguimientoRepository;

@Service
public class PedidoSeguimientoDAOImpl implements PedidoSeguimientoDAO {

    @Autowired
    private PedidoSeguimientoRepository pedidoSeguimientoRepository;

    @Override
    public List<PedidoSeguimiento> findByPedidoIdOrderByFechaCambioDesc(Integer pedidoId) {
        return pedidoSeguimientoRepository.findByPedidoIdOrderByFechaCambioDesc(pedidoId);
    }

    @Override
    public void save(PedidoSeguimiento pedidoSeguimiento) {
        pedidoSeguimientoRepository.save(pedidoSeguimiento);
    }

}
