package com.urbanfeet_backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedido_detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_zapatilla_variacion")
    private Zapatilla_variacion zapatilla_variacion;

    private Integer cantidad;
    private Double precioTotal;

    public Pedido_detalle() {
    }

    public Pedido_detalle(Integer id, Pedido pedido, Zapatilla_variacion zapatilla_variacion, Integer cantidad,
            Double precioTotal) {
        this.id = id;
        this.pedido = pedido;
        this.zapatilla_variacion = zapatilla_variacion;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Zapatilla_variacion getZapatilla_variacion() {
        return zapatilla_variacion;
    }

    public void setZapatilla_variacion(Zapatilla_variacion zapatilla_variacion) {
        this.zapatilla_variacion = zapatilla_variacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

}
