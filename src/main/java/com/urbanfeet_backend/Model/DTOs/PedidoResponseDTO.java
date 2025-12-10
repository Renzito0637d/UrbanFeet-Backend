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
    public String nombreUsuario;
    public String apellidoUsuario;
    public String emailUsuario;
    public String telefonoUsuario;

    public PedidoResponseDTO(Integer id, Integer userId, String estado,
            LocalDateTime fechaPedido,
            DireccionEnvioDTO direccionEnvio,
            List<PedidoDetalleResponseDTO> detalles, String metodoPago, String nombreUsuario, String apellidoUsuario,
            String emailUsuario, String telefonoUsuario) {
        this.id = id;
        this.userId = userId;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.direccionEnvio = direccionEnvio;
        this.detalles = detalles;
        this.metodoPago = metodoPago;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.emailUsuario = emailUsuario;
        this.telefonoUsuario = telefonoUsuario;
    }
}
