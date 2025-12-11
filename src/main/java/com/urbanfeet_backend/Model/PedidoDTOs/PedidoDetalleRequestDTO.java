package com.urbanfeet_backend.Model.PedidoDTOs;

public class PedidoDetalleRequestDTO {
    private Integer zapatillaVariacionId;
    private Integer cantidad;
    private Double precioTotal;

    public Integer getZapatillaVariacionId() {
        return zapatillaVariacionId;
    }

    public void setZapatillaVariacionId(Integer zapatillaVariacionId) {
        this.zapatillaVariacionId = zapatillaVariacionId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
