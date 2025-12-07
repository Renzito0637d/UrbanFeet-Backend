package com.urbanfeet_backend.Model.UserDTOs;

import java.util.Set;

import com.urbanfeet_backend.Entity.User;
import com.urbanfeet_backend.Entity.Enum.DocumentType;
import com.urbanfeet_backend.Entity.Enum.RoleName;

public class UserResponse {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String phone;
    private Boolean active;
    private DocumentType documentType;
    private String documentNumber;
    private Set<RoleName> roles;

    // Constructor vacío
    public UserResponse() {
    }

    // Constructor completo
    public UserResponse(Integer id, String nombre, String apellido, String email, String phone, Boolean active,
            DocumentType documentType, String documentNumber, Set<RoleName> roles) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.active = active;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.roles = roles;
    }

    // Método estático para convertir Entidad -> DTO (Mapeador)
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail(),
                user.getPhone(),
                user.getActive(),
                user.getDocumentType(),
                user.getDocumentNumber(),
                user.getRoles());
    }

    // Getters y Setters
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Set<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleName> roles) {
        this.roles = roles;
    }
}