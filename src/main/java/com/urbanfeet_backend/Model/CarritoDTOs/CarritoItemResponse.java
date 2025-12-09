package com.urbanfeet_backend.Model.CarritoDTOs;

public class CarritoItemResponse {

    private Integer id; // ID del item del carrito
    private Integer cantidad;

    // Datos planos de la variación (para evitar devolver la entidad completa)
    private Integer variacionId;
    private Double precioUnitario;

    // Opcional: Si tienes acceso al nombre del producto a través de la variación
    // private String nombreProducto;

    public CarritoItemResponse(Integer id, Integer cantidad, Integer variacionId, Double precioUnitario) {
        this.id = id;
        this.cantidad = cantidad;
        this.variacionId = variacionId;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters (o usa @Data de Lombok)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getVariacionId() {
        return variacionId;
    }

    public void setVariacionId(Integer variacionId) {
        this.variacionId = variacionId;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}