package com.urbanfeet_backend.Model.UserDTOs;

import com.urbanfeet_backend.Entity.Enum.DocumentType;
import com.urbanfeet_backend.Entity.Enum.RoleName;

public class UserUpdateRequest {
    private String nombre;
    private String apellido;
    private String phone;
    private DocumentType documentType;
    private String documentNumber;
    private RoleName role;

    // Getters y Setters
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}