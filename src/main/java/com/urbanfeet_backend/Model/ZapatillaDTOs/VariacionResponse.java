package com.urbanfeet_backend.Model.ZapatillaDTOs;

public record VariacionResponse(
        Integer id,
        String color,
        String imageUrl,
        Double precio,
        Integer stock,
        String talla,
        Integer zapatillaId) {
}