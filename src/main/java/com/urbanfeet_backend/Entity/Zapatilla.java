package com.urbanfeet_backend.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference; // <--- IMPORTANTE

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Zapatilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    private String descripcion;
    
    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;
    
    private String genero;
    private String tipo;

    // --- MEJORA CRÍTICA ---
    // @JsonManagedReference es el "padre". Evita la recursión infinita.
    @OneToMany(mappedBy = "zapatilla", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    @JsonManagedReference // <--- AÑADIR ESTO
    private List<Zapatilla_variacion> variaciones = new ArrayList<>();

    public int getStockTotal() {
        if (variaciones == null)
            return 0;
        return variaciones.stream()
                .mapToInt(v -> v.getStock() != null ? v.getStock() : 0)
                .sum();
    }

    // --- Métodos de ayuda para sincronizar la relación ---
    // (Estos son muy útiles para la capa DAO)
    public void addVariacion(Zapatilla_variacion variacion) {
        variaciones.add(variacion);
        variacion.setZapatilla(this);
    }

    public void removeVariacion(Zapatilla_variacion variacion) {
        variaciones.remove(variacion);
        variacion.setZapatilla(null);
    }

    // Constructores, Getters y Setters (los tuyos están bien)
    public Zapatilla() {
    }
    
    // (El resto de tus getters y setters...)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public List<Zapatilla_variacion> getVariaciones() { return variaciones; }
    public void setVariaciones(List<Zapatilla_variacion> variaciones) { this.variaciones = variaciones; }
}