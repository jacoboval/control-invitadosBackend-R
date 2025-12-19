package com.javasystems.invitacion.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "listaInvitados")
public class listaInvitados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String telefono;
    private Integer acompanantes;
    public String tokenAcceso;

    public boolean confirmado;

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }
    public boolean getConfirmado() {
        return confirmado;
    }


    public String getTokenAcceso() {
        return tokenAcceso;
    }

    public void setTokenAcceso(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }



    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

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

    public Integer getAcompanantes() {
        return acompanantes;
    }

    public void setAcompanantes(Integer acompanantes) {
        this.acompanantes = acompanantes;
    }



}