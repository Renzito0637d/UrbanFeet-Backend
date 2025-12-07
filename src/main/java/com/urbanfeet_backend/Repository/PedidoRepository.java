package com.urbanfeet_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.urbanfeet_backend.Entity.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.detalles WHERE p.user.id = :userId")
    List<Pedido> findPedidosConDetallesByUserId(@Param("userId") Integer userId);
}
