package com.urbanfeet_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.urbanfeet_backend.Entity.Sugerencia;

@Repository
public interface SugerenciaRepository extends JpaRepository<Sugerencia, Integer> {
}
