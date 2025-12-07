package com.urbanfeet_backend.Model.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponseDTO {
    public Integer id;
    public Integer userId;
    public String estado;
    public LocalDateTime fechaPedido;
    public DireccionEnvioDTO direccion_envio;
    public List<PedidoDetalleResponseDTO> detalles;

    public PedidoResponseDTO(Integer id, Integer userId, String estado,
            LocalDateTime fechaPedido,
            DireccionEnvioDTO direccion_envio,
            List<PedidoDetalleResponseDTO> detalles) {
        this.id = id;
        this.userId = userId;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.direccion_envio = direccion_envio;
        this.detalles = detalles;
    }
}
