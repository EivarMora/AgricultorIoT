package iser.apiOrion.serviceImplement;


import iser.apiOrion.DTO.DatosGraficaDTO;
import iser.apiOrion.auth.serviceImpl.JwtTokenProvider;
import iser.apiOrion.collection.Datos;
import iser.apiOrion.repository.DatosRepository;
import iser.apiOrion.repository.SensorRepository;
import iser.apiOrion.service.DatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DatosServiceImpl implements DatosService {

    /**
     * Repositorio de datos
     */
    @Autowired
    DatosRepository datosRepository;

    /**
     * Repositorio de sensores
     */
    @Autowired
    SensorRepository sensorRepository;

    /**
     * Servicio de jwt
     */
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    /**
     * Metodo que permite obtener los datos de un sensor en un rango de fechas
     * @param fechainicio fecha de inicio
     * @param fechafin fecha final
     * @param idSensor id del sensor
     * @return lista de datos
     */
    @Override
    public ResponseEntity<?> rangofecha(Date fechainicio, Date fechafin, String idSensor) {
        try {
            List<Datos> datos = datosRepository.findByIdSensorAndFechaBetween(idSensor, fechainicio, fechafin);
            List<DatosGraficaDTO> datosGraficaDTOList = new ArrayList<>();
            for(Datos dato : datos){
                DatosGraficaDTO datosGraficaDTO = new DatosGraficaDTO();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateFormat.format(dato.getFecha());
                System.out.println("Fecha por dato: "+date);
                datosGraficaDTO.setTime(date);
                datosGraficaDTO.setValue(dato.getValor());
                datosGraficaDTOList.add(datosGraficaDTO);
            }
            return ResponseEntity.ok(datosGraficaDTOList);
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    /**
     * Metodo que permite insertar un dato
     * @param idSensor id del sensor
     * @param valor valor del sensor
     * @param fecha fecha del dato
     * @return dato insertado
     */
    @Override
    public ResponseEntity<?> insertar(String idSensor, String valor, Date fecha) {
        try {
            Datos datos = new Datos();
            datos.setIdSensor(idSensor);
            datos.setValor(valor);
            datos.setFecha(fecha);

            return ResponseEntity.ok(datosRepository.save(datos));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }


}
