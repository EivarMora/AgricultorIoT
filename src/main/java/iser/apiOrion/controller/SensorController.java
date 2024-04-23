package iser.apiOrion.controller;

import iser.apiOrion.collection.Sensor;
import iser.apiOrion.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @GetMapping("/obtenerTodos")
    public ResponseEntity<?> obtenerTodos(){
        return sensorService.buscarTodos();
    }

    @GetMapping("/obtenerPorId")
    public ResponseEntity<?> obtenerPorId(String id){
        return sensorService.buscarPorId(id);
    }

    @PostMapping("/crearSensor")
    public ResponseEntity<?> crearSensor(Sensor sensor){
        return sensorService.crearSensor(sensor);
    }

    @PutMapping("/actualizarSensor")
    public ResponseEntity<?> actualizarSensor(Sensor sensor){
        return sensorService.actualizarSensor(sensor);
    }

    @DeleteMapping("/borrarSensor")
    public ResponseEntity<?> borrarSensor(String id){
        return sensorService.borrarSensor(id);
    }

}
