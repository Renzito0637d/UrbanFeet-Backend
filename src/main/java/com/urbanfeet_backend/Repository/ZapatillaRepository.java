package com.urbanfeet_backend.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.urbanfeet_backend.Entity.Zapatilla;

@Repository
public interface ZapatillaRepository extends JpaRepository<Zapatilla, Integer> {
    @EntityGraph(attributePaths = { "variaciones" })
    Page<Zapatilla> findByVariacionesIsNotEmpty(Pageable pageable);

    @Query("SELECT DISTINCT z FROM Zapatilla z " +
            "LEFT JOIN z.variaciones v " +
            "WHERE (:marcas IS NULL OR z.marca IN :marcas) " +
            "AND (:genero IS NULL OR z.genero = :genero) " +
            "AND (:tipo IS NULL OR z.tipo = :tipo) " +
            "AND (:talla IS NULL OR v.talla = :talla) " +
            "AND (:precioMin IS NULL OR v.precio >= :precioMin) " +
            "AND (:precioMax IS NULL OR v.precio <= :precioMax) " +
            "AND (size(z.variaciones) > 0)") // Asegura que tenga stock/variaciones
    Page<Zapatilla> findByFilters(
            @Param("marcas") List<String> marcas,
            @Param("genero") String genero,
            @Param("tipo") String tipo,
            @Param("talla") String talla,
            @Param("precioMin") Double precioMin,
            @Param("precioMax") Double precioMax,
            Pageable pageable);
}
