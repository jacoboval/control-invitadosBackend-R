package com.javasystems.invitacion.controller;

import com.javasystems.invitacion.model.InvitadoConfirmado;
import com.javasystems.invitacion.model.listaInvitados;
import com.javasystems.invitacion.repository.InvitadoConfirmadoRepository;
import com.javasystems.invitacion.repository.listaInvitadosRepository;
import com.javasystems.invitacion.service.InvitadoConfirmadoService;
import com.javasystems.invitacion.service.listaInvitadosService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rsvp")
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "https://control-invitados-backend.onrender.com")
public class InvitadoConfirmadoController{


    @Autowired
    private InvitadoConfirmadoRepository confirmadoRepo;
    private InvitadoConfirmadoService invitadoConfirmadoService;

    @Autowired
    private listaInvitadosRepository listaRepo;
    private listaInvitadosService listaInvitadosService;

    @Autowired
    private InvitadoConfirmadoService invitadoService;

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestBody InvitadoConfirmado data) {

        InvitadoConfirmadoService.ConfirmacionStatus status = invitadoService.procesarConfirmacion(data);

        switch (status) {
            case OK:
                return ResponseEntity.ok("Confirmación registrada correctamente.");
            case NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token inválido");
            case ALREADY_CONFIRMED:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este invitado ya confirmó previamente.");
            default:
                // Esto es solo para cubrir todos los casos del enum.
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de servidor.");
        }
    }
    @GetMapping("/listar")
    public ResponseEntity <List<InvitadoConfirmado>>InvitadoConfirmado() {
        List<InvitadoConfirmado> invitadocpnfirmado= invitadoService.listar();
        return ResponseEntity.ok(invitadocpnfirmado);
    }

}

