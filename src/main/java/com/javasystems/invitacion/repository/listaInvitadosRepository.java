package com.javasystems.invitacion.repository;

import com.javasystems.invitacion.model.listaInvitados;
import org.springframework.data.jpa.repository.JpaRepository;



public interface listaInvitadosRepository extends JpaRepository<listaInvitados, Long> {
    listaInvitados findByTokenAcceso(String tokenAcceso);
}