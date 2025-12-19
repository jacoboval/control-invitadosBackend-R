package com.javasystems.invitacion.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invitadoconfirmado")

public class InvitadoConfirmado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    //private String telefono;
    private Integer acompanantes;
    private String asistencia;
    @Column(columnDefinition = "TEXT")
    private String mensaje;

    public String tokenAcceso;


    public String getTokenAcceso() {
        return tokenAcceso;
    }

    public void setTokenAcceso(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }


/*private boolean confirmado;

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }*/

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

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }




/*
    public String getTokenAcceso() {
        return tokenAcceso;
    }

    public void setTokenAcceso(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }
*/
   /* public boolean isConfirmado() {
    }

    public void setConfirmado(boolean b) {
    }*/
}