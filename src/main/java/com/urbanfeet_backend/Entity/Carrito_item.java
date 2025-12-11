package com.urbanfeet_backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Carrito_item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "zapatilla_variacion_id")
    private Zapatilla_variacion zapatillaVariacion;

    private Integer cantidad;

    public Carrito_item() {
    }

    public Carrito_item(Integer id, Carrito carrito, Zapatilla_variacion zapatilla_variacion, Integer cantidad) {
        this.id = id;
        this.carrito = carrito;
        this.zapatillaVariacion = zapatilla_variacion;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Zapatilla_variacion getZapatilla_variacion() {
        return zapatillaVariacion;
    }

    public void setZapatilla_variacion(Zapatilla_variacion zapatilla_variacion) {
        this.zapatillaVariacion = zapatilla_variacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
}
