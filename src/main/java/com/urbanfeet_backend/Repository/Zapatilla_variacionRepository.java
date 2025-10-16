package com.urbanfeet_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.urbanfeet_backend.Entity.Zapatilla_variacion;

@Repository
public interface Zapatilla_variacionRepository extends JpaRepository<Zapatilla_variacion, Integer> {

}
