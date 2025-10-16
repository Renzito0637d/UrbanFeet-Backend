package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.urbanfeet_backend.DAO.Interfaces.VentaDAO;
import com.urbanfeet_backend.Entity.Venta;
import com.urbanfeet_backend.Repository.VentaRepository;

public class VentaDAOImpl implements VentaDAO {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public void save(Venta venta) {
        ventaRepository.save(venta);
    }

    @Override
    public Venta findById(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Venta venta) {
        ventaRepository.save(venta);
    }

    @Override
    public void deleteById(Integer id) {
        ventaRepository.deleteById(id);
    }

}
