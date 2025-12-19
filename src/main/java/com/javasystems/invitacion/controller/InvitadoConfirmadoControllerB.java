package com.javasystems.invitacion.controller;


import com.javasystems.invitacion.model.InvitadoConfirmado;
import com.javasystems.invitacion.model.listaInvitados;
import com.javasystems.invitacion.repository.InvitadoConfirmadoRepository;
import com.javasystems.invitacion.repository.listaInvitadosRepository;
import com.javasystems.invitacion.service.InvitadoConfirmadoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rsvp")
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "https://control-invitados-backend.onrender.com")

//https://control-invitados-backend.onrender.com
public class InvitadoConfirmadoControllerB {


    @Autowired
    private InvitadoConfirmadoRepository confirmadoRepo;

    @Autowired
    private listaInvitadosRepository listaRepo;

    @PostMapping("/confirmar2")
    public ResponseEntity<?> confirmar(@RequestBody InvitadoConfirmado data) {

        // 1. Buscar en listaInvitados usando el token
        listaInvitados invitadoLista = listaRepo.findByTokenAcceso(data.getTokenAcceso());



        if (invitadoLista == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Token inválido");
        }


        // 2. Verificar si ya había confirmado
        if (invitadoLista.getConfirmado() == true) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Este invitado ya confirmó previamente.");
        }

        // 3. Marcar como confirmado en la tabla 'listaInvitados'
        invitadoLista.setConfirmado(true);
        listaRepo.save(invitadoLista);

        // 4. Guardar respuesta en la tabla 'invitadoconfirmado'
        InvitadoConfirmado nuevo = new InvitadoConfirmado();
        nuevo.setNombre(data.getNombre());
        nuevo.setAcompanantes(data.getAcompanantes());
        nuevo.setAsistencia(data.getAsistencia());
        nuevo.setMensaje(data.getMensaje());
        nuevo.setTokenAcceso(data.getTokenAcceso());

        confirmadoRepo.save(nuevo);

        return ResponseEntity.ok("Confirmación registrada correctamente.");
    }
}

