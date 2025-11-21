package com.urbanfeet_backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Zapatilla_variacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El color no puede estar vacío")
    private String color;
    
    private String imageUrl;
    
    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    
    @NotBlank(message = "La talla no puede estar vacía")
    private String talla;

    // --- MEJORA CRÍTICA ---
    // @JsonBackReference es el "hijo". 
    // para evitar el bucle infinito.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zapatilla_id", nullable = false)
    @JsonBackReference
    private Zapatilla zapatilla;

    public Zapatilla_variacion() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }
    public Zapatilla getZapatilla() { return zapatilla; }
    public void setZapatilla(Zapatilla zapatilla) { this.zapatilla = zapatilla; }
}