package com.urbanfeet_backend.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;

@Entity
public class Zapatilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private String marca;
    private String genero;
    private String tipo;

    // Relación uno a muchos con zapatilla_variacion
    @OneToMany(mappedBy = "zapatilla", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    private List<Zapatilla_variacion> variaciones = new ArrayList<>();

    public int getStockTotal() {
        if (variaciones == null)
            return 0;
        return variaciones.stream()
                .mapToInt(v -> v.getStock() != null ? v.getStock() : 0)
                .sum();
    }

    public Zapatilla() {
    }

    public Zapatilla(Integer id, String nombre, String descripcion, String marca, String genero, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.genero = genero;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Zapatilla_variacion> getVariaciones() {
        return variaciones;
    }

    public void setVariaciones(List<Zapatilla_variacion> variaciones) {
        this.variaciones = variaciones;
    }
    
}
