package com.urbanfeet_backend.DAO.Interfaces;

import java.util.List;
import com.urbanfeet_backend.Entity.Zapatilla;

public interface ZapatillaDAO {
    
    List<Zapatilla> findAll();

    Zapatilla save(Zapatilla zapatilla);   // <-- antes era void

    Zapatilla findById(Integer id);

    void update(Zapatilla zapatilla);

    void deleteById(Integer id);
}
