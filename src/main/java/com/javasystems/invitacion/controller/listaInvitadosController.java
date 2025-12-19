package com.javasystems.invitacion.controller;

import com.javasystems.invitacion.model.listaInvitados;
import com.javasystems.invitacion.repository.listaInvitadosRepository;
import com.javasystems.invitacion.service.listaInvitadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "https://control-invitados-backend.onrender.com")
public class listaInvitadosController {

    @Autowired
    private listaInvitadosService listaInvitadosService;
    @Autowired
    private listaInvitadosRepository listaInvitadosRepository;


    //  CREAR: /admin (POST)
    @PostMapping
    public ResponseEntity<listaInvitados> agregarAListadeInvitados(@RequestBody listaInvitados listaInvitados){
        listaInvitados nuevo = listaInvitadosService.crearInvitado(listaInvitados);
        return  ResponseEntity.ok(nuevo);
    }


    // LISTAR: /admin/listarInvitados (GET)
        @GetMapping("/listarInvitados")
    public ResponseEntity<List<listaInvitados>>listarInvitados(){
        List <listaInvitados> listaInvitados = listaInvitadosService.listar();
        return ResponseEntity.ok(listaInvitados);
    }

    // BUSCAR POR ID: /admin/buscar-id/{id} (GET)
    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<listaInvitados> ObtenerPorId(@PathVariable Long id) {
        return listaInvitadosService.buscarInvitado(id)
                .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

    // BUSCAR POR TOKEN (Validación): /admin/token/{token} (GET)
    @GetMapping("/t/{token}")
    public ResponseEntity<?> obtenerPorToken(@PathVariable String token) {
        listaInvitados invitado = listaInvitadosRepository.findByTokenAcceso(token);

        if (invitado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Token inválido");
        }

        if (invitado.isConfirmado()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Esta invitación YA fue confirmada anteriormente.");
        }

        return ResponseEntity.ok(invitado);
    }

    // 5. ACTUALIZAR: /admin/{id} (PUT) 💡 ¡NUEVO!
    @PutMapping("/{id}")
    public ResponseEntity<listaInvitados> actualizarInvitado(
            @PathVariable Long id,
            @RequestBody listaInvitados invitadoActualizado) {

        listaInvitados actualizado = listaInvitadosService.actualizarInvitado(id, invitadoActualizado);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    // 6. ELIMINAR: /admin/{id} (DELETE) 💡 ¡NUEVO!
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (listaInvitadosService.eliminarInvitado(id)) {
            return ResponseEntity.ok("Invitado eliminado correctamente de la lista.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invitado no encontrado.");
        }
    }
    @GetMapping("/buscar/{token}")
    public ResponseEntity<?> buscarPorToken(@PathVariable String token) {

        listaInvitados listaInvitados = listaInvitadosRepository.findByTokenAcceso(token);

        if (listaInvitados == null) {
            return ResponseEntity.status(404)
                    .body("Token no válido");
        }
        return ResponseEntity.ok(listaInvitados);
    }
}
/*package com.javasystems.invitacion.controller;

import com.javasystems.invitacion.model.listaInvitados;
import com.javasystems.invitacion.repository.listaInvitadosRepository;
import com.javasystems.invitacion.service.listaInvitadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")

@CrossOrigin(origins = "*")
public class listaInvitadosController {

    @Autowired
    private listaInvitadosService listaInvitadosService;
    @Autowired
    private listaInvitadosRepository listaInvitadosRepository;


    //  crear invitado en la lista de invitados
    @PostMapping
    public ResponseEntity<listaInvitados> agregarAListadeInvitados(@RequestBody listaInvitados listaInvitados){
        listaInvitados nuevo = listaInvitadosService.crearInvitado(listaInvitados);
        return  ResponseEntity.ok(nuevo);
    }


        @GetMapping("/listarInvitados")
    public ResponseEntity<List<listaInvitados>>listarInvitados(){
        List <listaInvitados> listaInvitados = listaInvitadosService.listar();
        return ResponseEntity.ok(listaInvitados);
    }
    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<listaInvitados> ObtenerPorId(@PathVariable Long id) {
        return listaInvitadosService.buscarInvitado(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vista/{token}")
    public ResponseEntity<listaInvitados> cargarInvitadoPorToken(@PathVariable String token) {
        listaInvitados invitado = listaInvitadosRepository.findByTokenAcceso(token);
        if (invitado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invitado);
    }



    @GetMapping("/buscar/{token}")
    public ResponseEntity<?> obtenerPorToken(@PathVariable String token) {
        listaInvitados listaInvitados = listaInvitadosRepository.findByTokenAcceso(token);

        if (listaInvitados == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Token inválido"); //  anivel de consola se muetra el mensaje: Token inválido , anivel frontend semuestra el mensaje: Invitado no encontrado
        }

        if (listaInvitados.getConfirmado() == true) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Esta invitación YA fue confirmada anteriormente.");
        }else if (listaInvitados.getConfirmado() == false){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invitado aun no ha confirmad.");

        }

        return ResponseEntity.ok(listaInvitados);
    }



    /*
    @GetMapping("/buscar/{token}")
    public ResponseEntity<?> buscarPorToken(@PathVariable String token) {

        listaInvitados listaInvitados = listaInvitadosRepository.findByTokenAcceso(token);

        if (listaInvitados == null) {
           return ResponseEntity.status(404)
                    .body("Token no válido");
        }

        return ResponseEntity.ok(listaInvitados);

    }*/
//}

/*nuevo
{
        "nombre": "Daniel Alejandro Vélez Valencia",
        "acompanantes": 3,
        "telefono": "55 5894523"
        }

 */
/* confirmar
{
"nombre": "Daniel Alejandro Vélez Valencia",
"acompanantes": 3,
"asistencia": "si",
"mensaje": "Nos vemos tio, en la fiesta",
"tokenAcceso": "84896cda-3a69-458b-a469-1ae3f81a2db3"
}
* */


