package com.urbanfeet_backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Zapatilla_variacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String color;
    private String imageUrl;
    private Double precio;
    private Integer stock;
    private String talla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zapatilla_id", nullable = false)
    private Zapatilla zapatilla;

    public Zapatilla_variacion() {
    }

    public Zapatilla_variacion(Integer id, String color, String imageUrl, Double precio, Integer stock, String talla, Zapatilla zapatilla) {
        this.id = id;
        this.color = color;
        this.imageUrl = imageUrl;
        this.precio = precio;
        this.stock = stock;
        this.talla = talla;
        this.zapatilla = zapatilla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public Zapatilla getZapatilla() {
        return zapatilla;
    }

    public void setZapatilla(Zapatilla zapatilla) {
        this.zapatilla = zapatilla;
    }
    
}
