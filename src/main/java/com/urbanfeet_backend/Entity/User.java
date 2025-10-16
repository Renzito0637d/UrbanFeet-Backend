package com.urbanfeet_backend.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.urbanfeet_backend.Entity.Enum.DocumentType;
import com.urbanfeet_backend.Entity.Enum.RoleName;
import com.urbanfeet_backend.Model.RegisterRequest;
import com.urbanfeet_backend.Model.ValidDocumentNumber;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_doc", columnNames = { "documentType", "documentNumber" })
}, indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true),
        @Index(name = "idx_user_doc", columnList = "documentType, documentNumber")
})
// Valida que el número del documento coincida con el tipo (ver clase validador
// abajo)
@ValidDocumentNumber
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String apellido;

    @Email
    @Column(nullable = false, length = 160, unique = true)
    private String email;

    @Column(length = 32)
    private String phone;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private DocumentType documentType = DocumentType.DNI;

    // String para preservar ceros a la izquierda (DNI = 8 dígitos)
    @NaturalId
    @Column(nullable = false, length = 32)
    private String documentNumber;

    @ElementCollection(targetClass = RoleName.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = @UniqueConstraint(name = "uk_user_role", columnNames = {
            "user_id", "role" }))
    @Column(name = "role", nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private Set<RoleName> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Direccion> direcciones;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Carrito carrito;

    /*
     * Normalización ligera:
     * - DNI: quitar espacios
     * - PASSPORT: trim y upper (los pasaportes suelen ser case-insensitive)
     */
    @PrePersist
    @PreUpdate
    private void normalizeDocument() {
        if (documentNumber != null) {
            String n = documentNumber.trim();
            this.documentNumber = n;
        }
    }

    public User() {
    }

    public User(Integer id, String nombre, String apellido, String email, String phone, String passwordHash,
            String documentNumber) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.documentNumber = documentNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        return roles.stream()
                .filter(Objects::nonNull)
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toList());
    }

    // Este método devuelve el nombre de usuario del usuario.
    @Override
    public String getUsername() {
        return this.email;
    }

    // Este método devuelve la contraseña del usuario.
    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    // Los siguientes métodos devuelven valores predeterminados para las propiedades
    // de seguridad del usuario.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Este método indica si la cuenta del usuario está bloqueada o no.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Este método indica si las credenciales del usuario han expirado o no.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Este método indica si la cuenta del usuario está habilitada o no.
    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.active);
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public static class Builder {
        private Integer id;
        private String nombre;
        private String apellido;
        private String email;
        private String phone;
        private String passwordHash;
        private Boolean active = true;
        private DocumentType documentType = DocumentType.DNI;
        private String documentNumber;
        private Set<RoleName> roles = new HashSet<>();

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Builder documentType(DocumentType documentType) {
            this.documentType = documentType;
            return this;
        }

        public Builder documentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
            return this;
        }

        public Builder roles(Set<RoleName> roles) {
            this.roles = roles;
            return this;
        }

        public Builder addRole(RoleName role) {
            if (this.roles == null) {
                this.roles = new HashSet<>();
            }
            this.roles.add(role);
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(this.id);
            user.setNombre(this.nombre);
            user.setApellido(this.apellido);
            user.setEmail(this.email);
            user.setPhone(this.phone);
            user.setPasswordHash(this.passwordHash);
            user.setActive(this.active);
            user.setDocumentType(this.documentType);
            user.setDocumentNumber(this.documentNumber);
            user.setRoles(this.roles);
            return user;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static User from(RegisterRequest r, String encodedPassword, RoleName role) {
        return User.builder()
                .nombre(r.getNombre())
                .apellido(r.getApellido())
                .email(r.getEmail())
                .phone(r.getPhone())
                .passwordHash(encodedPassword)
                .documentType(r.getDocumentType())
                .documentNumber(r.getDocumentNumber())
                .active(true)
                .addRole(role)
                .build();
    }

}
