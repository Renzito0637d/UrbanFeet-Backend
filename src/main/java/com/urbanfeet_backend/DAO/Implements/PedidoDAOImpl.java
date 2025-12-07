package com.urbanfeet_backend.DAO.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.urbanfeet_backend.DAO.Interfaces.PedidoDAO;
import com.urbanfeet_backend.Entity.Pedido;
import com.urbanfeet_backend.Repository.PedidoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PedidoDAOImpl implements PedidoDAO {

    @Autowired
    private PedidoRepository pedidoRepository;

    // <-- Inyectamos EntityManager para consultas personalizadas
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public void save(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Override
    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Override
    public void deleteById(Integer id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public List<Pedido> findAllWithDetallesByUserId(Integer userId) {
        return entityManager.createQuery(
                "SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.detalles WHERE p.user.id = :userId",
                Pedido.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Pedido findByIdWithDetalles(Integer id) {
        return entityManager.createQuery(
                "SELECT p FROM Pedido p LEFT JOIN FETCH p.detalles WHERE p.id = :id", Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
