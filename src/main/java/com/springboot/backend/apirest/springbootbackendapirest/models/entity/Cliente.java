package com.springboot.backend.apirest.springbootbackendapirest.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "no debe estar vacio")
    @Size(min = 4, message = "debe tener por lo menos 4 caracteres")
    @Column(nullable = false)
    private String nombre;
    @NotEmpty(message = "no debe estar vacio")
    @Size(min = 4, message = "debe tener por lo menos 4 caracteres")
    private String apellido;
    @NotEmpty(message = "no debe estar vacio")
    @Email(message = "debe ser un email bien formado")
    @Column(nullable = false, unique = true)
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }
}
