package com.urbanfeet_backend.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.urbanfeet_backend.Entity.Zapatilla;

@Repository
public interface ZapatillaRepository extends JpaRepository<Zapatilla, Integer> {
    @EntityGraph(attributePaths = {"variaciones"})
    Page<Zapatilla> findByVariacionesIsNotEmpty(Pageable pageable);
}
