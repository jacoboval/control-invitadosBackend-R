package com.javasystems.invitacion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class MainController {
    @GetMapping("/")
    public String home(){
        return "Backend Funcionando Correctamente";
    }
}
