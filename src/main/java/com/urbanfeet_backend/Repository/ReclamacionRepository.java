package com.urbanfeet_backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urbanfeet_backend.Entity.Reclamacion;

public interface ReclamacionRepository extends JpaRepository<Reclamacion, Integer> {
    List<Reclamacion> findByUser_Id(Integer userId);
}
