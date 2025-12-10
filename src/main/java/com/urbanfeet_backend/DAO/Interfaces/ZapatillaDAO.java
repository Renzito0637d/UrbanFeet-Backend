package com.urbanfeet_backend.DAO.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.urbanfeet_backend.Entity.Zapatilla;

public interface ZapatillaDAO {

    Page<Zapatilla> findAll(Pageable pageable);

    Page<Zapatilla> findByVariacionesIsNotEmpty(Pageable pageable);

    Zapatilla save(Zapatilla zapatilla); // <-- antes era void

    Zapatilla findById(Integer id);

    Zapatilla update(Zapatilla zapatilla);

    void deleteById(Integer id);
}
