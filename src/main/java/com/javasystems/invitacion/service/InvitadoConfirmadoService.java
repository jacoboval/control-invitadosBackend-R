/*package com.javasystems.invitacion.service;

import com.javasystems.invitacion.model.InvitadoConfirmado;
import com.javasystems.invitacion.repository.InvitadoConfirmadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvitadoConfirmadoService {


    @Autowired
    private InvitadoConfirmadoRepository invitadoRepository;

    public static void save(InvitadoConfirmado invitadoDB) {

    }


    public InvitadoConfirmado guardar(InvitadoConfirmado invitado){

        return invitadoRepository.save(invitado);
    }


    public List<InvitadoConfirmado> listar() {
        return invitadoRepository.findAll();
    }


    public Optional<InvitadoConfirmado> buscarInvitado(Long id){
        return invitadoRepository.findById(id);
    }

    public boolean eliminar(Long id){
        if(invitadoRepository.existsById(id)){
            invitadoRepository.deleteById(id);
            return true;
        }
        return  false;
    }
    public InvitadoConfirmado actualizar(Long id, InvitadoConfirmado datosActualizados){
        InvitadoConfirmado invitado = invitadoRepository.findById(id).orElse(null);
        if(invitado == null){
            return null;
        }
        invitado.setNombre(datosActualizados.getNombre());
        invitado.setAcompanantes(datosActualizados.getAcompanantes());
        invitado.setAsistencia(datosActualizados.getAsistencia());
        invitado.setMensaje(datosActualizados.getMensaje());

        return  invitadoRepository.save(invitado);
    }

    /*public Optional<Object> findById(Long id) {
    }
//}
*/
package com.javasystems.invitacion.service;

import com.javasystems.invitacion.model.InvitadoConfirmado;
import com.javasystems.invitacion.model.listaInvitados;
import com.javasystems.invitacion.repository.InvitadoConfirmadoRepository;
import com.javasystems.invitacion.repository.listaInvitadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante

import java.util.List;

@Service
public class InvitadoConfirmadoService {

    @Autowired
    private InvitadoConfirmadoRepository confirmadoRepo;

    @Autowired
    private listaInvitadosRepository listaRepo;

    public List<InvitadoConfirmado> listar() {
        return confirmadoRepo.findAll();
    }

    // Enum para manejar las respuestas de negocio (como hicimos antes)
    public enum ConfirmacionStatus { OK, NOT_FOUND, ALREADY_CONFIRMED }

    // 🏆 SOLUCIÓN: Usar @Transactional aquí
    @Transactional
    public ConfirmacionStatus procesarConfirmacion(InvitadoConfirmado data) {

        // 1. Buscar en listaInvitados usando el token
        listaInvitados invitadoLista = listaRepo.findByTokenAcceso(data.getTokenAcceso());

        if (invitadoLista == null) {
            return ConfirmacionStatus.NOT_FOUND;
        }

        // 2. Verificar si ya había confirmado
        if (invitadoLista.isConfirmado()) {
            return ConfirmacionStatus.ALREADY_CONFIRMED;
        }

        // 3. Marcar como confirmado en la tabla 'listaInvitados'
        invitadoLista.setConfirmado(true);
        // Cuando el metodo termina (y no hay excepción), Spring realiza el COMMIT
        // gracias a la anotación @Transactional. La llamada a save() ayuda a
        // asegurar que se ejecute la actualización inmediatamente.
        listaRepo.save(invitadoLista);

        // 4. Guardar respuesta en la tabla 'invitadoconfirmado'
        InvitadoConfirmado nuevo = new InvitadoConfirmado();
        nuevo.setNombre(invitadoLista.getNombre()); // Usamos el nombre de la lista original
        nuevo.setAcompanantes(data.getAcompanantes());
        nuevo.setAsistencia(data.getAsistencia());
        nuevo.setMensaje(data.getMensaje());
        nuevo.setTokenAcceso(data.getTokenAcceso());

        confirmadoRepo.save(nuevo);

        return ConfirmacionStatus.OK;
    }

    // ... (otros métodos del servicio) ...
}

