package com.urbanfeet_backend.Model.ZapatillaDTOs;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record VariacionRequest(
                @NotBlank(message = "El color es obligatorio") String color,

                String imageUrl,

                @NotNull(message = "El precio es obligatorio") @Min(value = 0, message = "El precio no puede ser negativo") Double precio,

                @NotNull(message = "El stock es obligatorio") @Min(value = 0, message = "El stock no puede ser negativo") Integer stock,

                @NotEmpty(message = "La talla es obligatoria") List<String> tallas) {
}