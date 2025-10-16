package com.urbanfeet_backend.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User user;

    private LocalDateTime fechaPedido;

    private String estado;

    @Embedded
    private Direccion_envio direccion_envio;  // Clonamos la dirección en el pedido

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Pedido_detalle> detalles;

    public Pedido() {
    }

    public Pedido(Integer id, User user, LocalDateTime fechaPedido, String estado, Direccion_envio direccion_envio, List<Pedido_detalle> detalles) {
        this.id = id;
        this.user = user;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.direccion_envio = direccion_envio;
        this.detalles = detalles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Direccion_envio getDireccion_envio() {
        return direccion_envio;
    }

    public void setDireccion_envio(Direccion_envio direccion_envio) {
        this.direccion_envio = direccion_envio;
    }

    public List<Pedido_detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Pedido_detalle> detalles) {
        this.detalles = detalles;
    }
    

}
