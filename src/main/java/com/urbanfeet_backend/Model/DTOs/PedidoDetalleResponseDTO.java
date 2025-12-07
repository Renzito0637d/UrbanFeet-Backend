package com.urbanfeet_backend.Model.DTOs;

public class PedidoDetalleResponseDTO {
    public Integer id;
    public Integer zapatillaVariacionId;
    public Integer cantidad;
    public Double precioTotal;

    public PedidoDetalleResponseDTO(Integer id, Integer zapatillaVariacionId, Integer cantidad, Double precioTotal) {
        this.id = id;
        this.zapatillaVariacionId = zapatillaVariacionId;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }
}
