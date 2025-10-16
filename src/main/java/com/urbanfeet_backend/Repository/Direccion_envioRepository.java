package com.urbanfeet_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.urbanfeet_backend.Entity.Direccion_envio;

@Repository
public interface Direccion_envioRepository extends JpaRepository<Direccion_envio, Integer> {
}