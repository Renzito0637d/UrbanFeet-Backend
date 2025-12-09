package com.urbanfeet_backend.Model.SugerenciaDTOs;

public class SugerenciaRequest {

    private String mensaje;
    private String asunto;

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
