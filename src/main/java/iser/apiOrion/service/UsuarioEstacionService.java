package iser.apiOrion.service;

import org.springframework.http.ResponseEntity;

public interface UsuarioEstacionService {

    public ResponseEntity<?> buscarHibernaderosUsuario(String id);

    ResponseEntity<?> buscarPorId(String id);

    ResponseEntity<?> crearUsuarioHibernadero(String idUsuario, String idHibernadero);

    ResponseEntity<?> borrarUsuarioHibernadero(String id);

    ResponseEntity<?> buscarPorUsuario(String idUsuario);

    ResponseEntity<?> buscarPorHibernadero(String idHibernadero);

    ResponseEntity<?> buscarUsuarioSinInvernadero(String idHibernadero);


}