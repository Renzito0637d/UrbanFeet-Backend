package com.urbanfeet_backend.Model.ZapatillaDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ZapatillaRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres") String nombre,

        String descripcion,

        @NotBlank(message = "La marca es obligatoria") String marca,

        @NotBlank(message = "El género es obligatorio") String genero,

        @NotBlank(message = "El tipo es obligatorio") String tipo) {
}