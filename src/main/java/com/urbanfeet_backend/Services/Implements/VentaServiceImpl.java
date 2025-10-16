package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.DAO.Interfaces.VentaDAO;
import com.urbanfeet_backend.Entity.Venta;
import com.urbanfeet_backend.Services.Interfaces.VentaService;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaDAO ventaDao;

    @Override
    public List<Venta> obtenerTodo() {
        return ventaDao.findAll();
    }

    @Override
    public void guardar(Venta venta) {
        ventaDao.save(venta);
    }

    @Override
    public Venta buscarPorId(Integer id) {
        return ventaDao.findById(id);
    }

    @Override
    public void actualizar(Venta venta) {
        ventaDao.update(venta);
    }

    @Override
    public void eliminarPorId(Integer id) {
        ventaDao.deleteById(id);
    }

}