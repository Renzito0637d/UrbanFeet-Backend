package com.urbanfeet_backend.Model.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponseDTO {
    public Integer id;
    public Integer userId;
    public String estado;
    public LocalDateTime fechaPedido;
    public DireccionEnvioDTO direccionEnvio;
    public List<PedidoDetalleResponseDTO> detalles;
    public String metodoPago;

    public PedidoResponseDTO(Integer id, Integer userId, String estado,
            LocalDateTime fechaPedido,
            DireccionEnvioDTO direccionEnvio,
            List<PedidoDetalleResponseDTO> detalles, String metodoPago) {
        this.id = id;
        this.userId = userId;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.direccionEnvio = direccionEnvio;
        this.detalles = detalles;
        this.metodoPago = metodoPago;
    }
}
