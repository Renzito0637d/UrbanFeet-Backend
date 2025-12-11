package com.urbanfeet_backend.Model.ZapatillaDTOs;

import java.util.List;

public class ZapatillaResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String marca;
    private String genero;
    private String tipo;
    private List<VariacionResponse> variaciones;

    public ZapatillaResponse() {
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

    public List<VariacionResponse> getVariaciones() {
        return variaciones;
    }

    public void setVariaciones(List<VariacionResponse> variaciones) {
        this.variaciones = variaciones;
    }
}