package com.urbanfeet_backend.Model.AuthDTOs;

import com.urbanfeet_backend.Entity.Enum.DocumentType;

public class RegisterRequest {

    private String nombre;
    private String apellido;
    private String email;
    private String phone;
    private String password;
    private DocumentType documentType;
    private String documentNumber;

    public RegisterRequest() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public RegisterRequest(String nombre, String apellido, String email, String phone, String password,
            DocumentType documentType, String documentNumber) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
    }

}
