package com.javasystems.invitacion.repository;

import com.javasystems.invitacion.model.InvitadoConfirmado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitadoConfirmadoRepository extends JpaRepository<InvitadoConfirmado,Long> {
 /*   static InvitadoConfirmado findByTokenAcceso(String tokenAcceso) {
        return null;
    }
*/

}