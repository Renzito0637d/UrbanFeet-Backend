package com.urbanfeet_backend.Model.PedidoDTOs;

public class PedidoDetalleResponseDTO {
    public Integer id;
    public Integer zapatillaVariacionId;
    public Integer cantidad;
    public Double precioTotal;
    public String nombreProducto;
    public String marca;
    public String color;
    public String tall;

    public String imgUrl;

    public PedidoDetalleResponseDTO(Integer id, Integer zapatillaVariacionId, Integer cantidad, Double precioTotal,
            String nombreProducto, String marca, String color, String tall, String imgUrl) {
        this.id = id;
        this.zapatillaVariacionId = zapatillaVariacionId;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.nombreProducto = nombreProducto;
        this.marca = marca;
        this.color = color;
        this.tall = tall;
        this.imgUrl = imgUrl;
    }
}
