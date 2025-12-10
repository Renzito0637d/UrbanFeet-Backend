package com.urbanfeet_backend.Model.VentaDTOs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record VentaResponseDTO(
        Long id,
        LocalDate fecha,
        LocalTime hora,
        String estadoPago,
        BigDecimal montoPagado,
        String metodoPago,

        Integer pedidoId,
        String nombreUsuario,
        String apellidoUsuario,
        String emailUsuario) {
}