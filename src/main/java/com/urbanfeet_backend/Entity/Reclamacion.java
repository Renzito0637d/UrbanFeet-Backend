package com.urbanfeet_backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Reclamacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String producto;

    private Double montoReclamado;

    private String tipoMensaje;

    private String detalleReclamo;

    private String solucionPropuesta;

    private LocalDateTime fechaRegistro;

    private String estado;

    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Reclamacion() {
    }

    public Reclamacion(Integer id, String producto, Double montoReclamado, String tipoMensaje,
            String detalleReclamo, String solucionPropuesta,
            LocalDateTime fechaRegistro, String estado, String direccion, User user) {

        this.id = id;
        this.producto = producto;
        this.montoReclamado = montoReclamado;
        this.tipoMensaje = tipoMensaje;
        this.detalleReclamo = detalleReclamo;
        this.solucionPropuesta = solucionPropuesta;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.direccion = direccion;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public String getProducto() {
        return producto;
    }

    public Double getMontoReclamado() {
        return montoReclamado;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public String getDetalleReclamo() {
        return detalleReclamo;
    }

    public String getSolucionPropuesta() {
        return solucionPropuesta;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public void setMontoReclamado(Double montoReclamado) {
        this.montoReclamado = montoReclamado;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public void setDetalleReclamo(String detalleReclamo) {
        this.detalleReclamo = detalleReclamo;
    }

    public void setSolucionPropuesta(String solucionPropuesta) {
        this.solucionPropuesta = solucionPropuesta;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
