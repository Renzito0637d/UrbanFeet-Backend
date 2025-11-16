package com.urbanfeet_backend.Model;

public class CarritoItemRequest {
    
    private Integer carritoId;
    private Integer zapatillaVariacionId;
    private Integer cantidad;

    public Integer getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(Integer carritoId) {
        this.carritoId = carritoId;
    }

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
}