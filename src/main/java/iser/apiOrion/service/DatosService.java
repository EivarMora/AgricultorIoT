package iser.apiOrion.service;

import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface DatosService {

    ResponseEntity<?> rangofecha(Date fechainicio, Date fechafin, String idSensor);


    ResponseEntity<?> insertar(String idSensor, String valor, Date fecha);

}
