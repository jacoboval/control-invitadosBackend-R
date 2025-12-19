

/*package com.javasystems.invitacion.service;

import com.javasystems.invitacion.model.listaInvitados;
import com.javasystems.invitacion.repository.listaInvitadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class listaInvitadosService {

    @Autowired
    private listaInvitadosRepository listaInvitadosRepository;


    public listaInvitados crearInvitado(listaInvitados listaInvitados) {
        listaInvitados.setTokenAcceso(UUID.randomUUID().toString());
        return listaInvitadosRepository.save(listaInvitados);
    }


    public List<listaInvitados> listar(){
        return listaInvitadosRepository.findAll();
    }

    public Optional<listaInvitados> buscarInvitado(Long id){
        return listaInvitadosRepository.findById(id);
    }

}*/
package com.javasystems.invitacion.service;

import com.javasystems.invitacion.model.listaInvitados;
import com.javasystems.invitacion.repository.listaInvitadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional; // 💡 Lo añadimos por si se usa

@Service
public class listaInvitadosService {

    @Autowired
    private listaInvitadosRepository listaInvitadosRepository;


    // 1. CREAR: Genera token y guarda
    public listaInvitados crearInvitado(listaInvitados listaInvitados) {
        // Aseguramos que el token se genere solo una vez
        if (listaInvitados.getTokenAcceso() == null || listaInvitados.getTokenAcceso().isEmpty()) {
            listaInvitados.setTokenAcceso(UUID.randomUUID().toString());
        }
        return listaInvitadosRepository.save(listaInvitados);
    }


    // 2. LISTAR
    public List<listaInvitados> listar(){
        return listaInvitadosRepository.findAll();
    }

    // 3. BUSCAR POR ID
    public Optional<listaInvitados> buscarInvitado(Long id){
        return listaInvitadosRepository.findById(id);
    }

    // 4. ACTUALIZAR: Implementación de la actualización (CRUD)
    @Transactional
    public listaInvitados actualizarInvitado(Long id, listaInvitados datosActualizados) {
        // Buscar el invitado existente
        listaInvitados invitadoExistente = listaInvitadosRepository.findById(id).orElse(null);

        if (invitadoExistente == null) {
            return null; // No encontrado
        }

        // Aplicar las actualizaciones
        invitadoExistente.setNombre(datosActualizados.getNombre());
        invitadoExistente.setTelefono(datosActualizados.getTelefono());
        invitadoExistente.setAcompanantes(datosActualizados.getAcompanantes());
        // El estado 'confirmado' y los campos de asistencia se actualizan en InvitadoConfirmadoService
        // Aquí solo se actualizan los campos administrativos

        // Guardar y retornar
        return listaInvitadosRepository.save(invitadoExistente);
    }

    // 5. ELIMINAR: Implementación de la eliminación (CRUD)
    public boolean eliminarInvitado(Long id) {
        if (listaInvitadosRepository.existsById(id)) {
            listaInvitadosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}