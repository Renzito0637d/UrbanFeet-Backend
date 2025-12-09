package com.urbanfeet_backend.Model.CarritoDTOs;

public record CarritoItemDetailResponse(
        Integer id,
        Integer cantidad,
        Double subtotal,

        Integer variacionId,
        String color,
        String talla,
        Double precioUnitario,
        String imageUrl,
        Integer stockDisponible,

        String nombreProducto,
        String marca) {
}