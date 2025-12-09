package com.urbanfeet_backend.Model.DTOs;

import java.util.List;

public class PedidoRequestDTO {
    private Integer direccionId;
    private String metodoPago;
    private List<PedidoDetalleRequestDTO> detalles;

    public Integer getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(Integer direccionId) {
        this.direccionId = direccionId;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<PedidoDetalleRequestDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDetalleRequestDTO> detalles) {
        this.detalles = detalles;
    }
}
