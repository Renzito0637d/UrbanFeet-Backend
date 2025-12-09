package com.urbanfeet_backend.Model.CarritoDTOs;

public class CarritoItemRequest {
    
    private Integer zapatillaVariacionId;
    private Integer cantidad;

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