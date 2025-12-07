package com.urbanfeet_backend.Model;

import com.urbanfeet_backend.Entity.Direccion;

public record DireccionDTO(
        Integer id,
        String calle,
        String distrito,
        String provincia,
        String departamento,
        String referencia) {

    public static DireccionDTO fromEntity(Direccion d) {
        return new DireccionDTO(
                d.getId(),
                d.getCalle(),
                d.getDistrito(),
                d.getProvincia(),
                d.getDepartamento(),
                d.getReferencia());
    }
}
