package com.urbanfeet_backend.Model.DIreccionDTOs;

import com.urbanfeet_backend.Entity.Direccion_envio;

public class DireccionEnvioDTO {
    public String calle;
    public String distrito;
    public String provincia;
    public String departamento;
    public String referencia;

    public DireccionEnvioDTO(String calle, String distrito, String provincia,
            String departamento, String referencia) {
        this.calle = calle;
        this.distrito = distrito;
        this.provincia = provincia;
        this.departamento = departamento;
        this.referencia = referencia;
    }

    public static DireccionEnvioDTO fromEntity(Direccion_envio d) {
        if (d == null)
            return null;

        return new DireccionEnvioDTO(
                d.getCalle(),
                d.getDistrito(),
                d.getProvincia(),
                d.getDepartamento(),
                d.getReferencia());
    }
}
