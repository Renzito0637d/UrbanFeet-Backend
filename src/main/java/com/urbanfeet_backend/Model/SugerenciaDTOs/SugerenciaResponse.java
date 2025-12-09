package com.urbanfeet_backend.Model.SugerenciaDTOs;

import java.time.LocalDateTime;

public class SugerenciaResponse {

    private Integer id;
    private String asunto;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private String estado;
    private Integer userId;

    public SugerenciaResponse() {}

    public SugerenciaResponse(Integer id, String asunto, String mensaje, LocalDateTime fechaEnvio, String estado, Integer userId) {
        this.id = id;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
        this.userId = userId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
}
