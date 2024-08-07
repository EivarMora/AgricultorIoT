package iser.apiOrion.serviceImplement;

import iser.apiOrion.DTO.SensorDTO;
import iser.apiOrion.collection.Estacion;
import iser.apiOrion.collection.Sensor;
import iser.apiOrion.repository.EstacionRepository;
import iser.apiOrion.repository.SensorRepository;
import iser.apiOrion.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static iser.apiOrion.constant.messageConstant.buildMessage;

@Service
public class SensorServiceImpl implements SensorService {

    /**
     * Repositorio de sensores
     */
    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Repositorio de estaciones
     */
    @Autowired
    EstacionRepository estacionRepository;


    /**
     * Metodo que permite obtener todos los sensores
     *
     * @return lista de sensores
     */
    @Override
    public ResponseEntity<?> buscarTodos() {
        try {
            System.out.println("Buscando todos los sensores");
            List<Sensor> sensors = sensorRepository.findAll();
            List<SensorDTO> sensorDTOList = new ArrayList<>();
            String ubicacion = "";
            System.out.println("Sensores encontrados: " + sensors.size());
            for(Sensor sensor : sensors){
                SensorDTO sensorDTO = new SensorDTO();
                sensorDTO.setId(sensor.getId());
                sensorDTO.setIdEstacion(sensor.getIdEstacion());
                sensorDTO.setNombre(sensor.getNombre());
                sensorDTO.setDescripcion(sensor.getDescripcion());
                sensorDTO.setConfig(sensor.isConfig());
                if (sensor.getIdEstacion() != null) {
                    Optional<Estacion> estacion = estacionRepository.findById(sensor.getIdEstacion());
                    ubicacion = estacion.map(value -> value.getCiudad() + " - " + value.getDepartamento()).orElse("No se encontro la ubicacion");
                    sensorDTO.setUbicacion(ubicacion);
                }
                sensorDTOList.add(sensorDTO);
            }

            return ResponseEntity.ok(sensorDTOList);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Metodo que permite obtener un sensor por su id
     *
     * @param id id del sensor
     * @return sensor
     */
    @Override
    public ResponseEntity<?> buscarPorId(String id) {
        try {
            Optional<Sensor> sensor = sensorRepository.findById(id);
            if (sensor.isEmpty()) {
                return ResponseEntity.badRequest().body(buildMessage("Sensor no encontrado"));
            }

            SensorDTO sensorDTO = new SensorDTO();
            sensorDTO.setId(sensor.get().getId());
            sensorDTO.setIdEstacion(sensor.get().getIdEstacion());
            sensorDTO.setNombre(sensor.get().getNombre());
            sensorDTO.setDescripcion(sensor.get().getDescripcion());
            sensorDTO.setConfig(sensor.get().isConfig());
            if (sensor.get().getIdEstacion() == null) {
                Optional<Estacion> estacion = estacionRepository.findById(sensor.get().getIdEstacion());
                String ubicacion = estacion.map(value -> value.getCiudad() + " - " + value.getDepartamento()).orElse("No se encontro la ubicacion");
                sensorDTO.setUbicacion(ubicacion);
            }
            return ResponseEntity.ok(sensorDTO);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Metodo que permite crear un sensor
     *
     * @param sensor sensor a crear
     * @return sensor creado
     */
    @Override
    public ResponseEntity<?> crearSensor(Sensor sensor) {
        try {
            sensorRepository.save(sensor);
            return ResponseEntity.ok(sensor);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Metodo que permite actualizar un sensor
     *
     * @param sensor sensor a actualizar
     * @return sensor actualizado
     */
    @Override
    public ResponseEntity<?> actualizarSensor(Sensor sensor) {
        try {
            sensorRepository.save(sensor);
            return ResponseEntity.ok(sensor);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Metodo que permite borrar un sensor
     *
     * @param id id del sensor
     * @return sensor borrado
     */
    @Override
    public ResponseEntity<?> borrarSensor(String id) {
        try {
            sensorRepository.deleteById(id);
            return ResponseEntity.ok(buildMessage("Sensor borrado"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Metodo que permite obtener los sensores de una estacion
     *
     * @param idEstacion id de la estacion
     * @return lista de sensores
     */
    @Override
    public ResponseEntity<?> buscarPorEstacion(String idEstacion) {
        try {
            List<Sensor> sensors = sensorRepository.findByIdEstacion(idEstacion);
            List<SensorDTO> sensorDTOList = new ArrayList<>();
            String ubicacion = "";
            for(Sensor sensor : sensors){
                SensorDTO sensorDTO = new SensorDTO();
                sensorDTO.setId(sensor.getId());
                sensorDTO.setIdEstacion(sensor.getIdEstacion());
                sensorDTO.setNombre(sensor.getNombre());
                sensorDTO.setDescripcion(sensor.getDescripcion());
                sensorDTO.setConfig(sensor.isConfig());
                if (sensor.getIdEstacion() != null) {
                    Optional<Estacion> estacion = estacionRepository.findById(sensor.getIdEstacion());
                    ubicacion = estacion.map(value -> value.getCiudad() + " - " + value.getDepartamento()).orElse("No se encontro la ubicacion");
                    sensorDTO.setUbicacion(ubicacion);
                }
                sensorDTOList.add(sensorDTO);
            }
            return ResponseEntity.ok(sensorDTOList);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Metodo que permite obtener los sensores asociados a un usuario
     *
     * @param idUsuario id del usuario
     * @return lista de sensores
     */
    @Override
    public ResponseEntity<?> buscarPorUsuario(String idUsuario) {
        try {
            List<Estacion> estaciones = estacionRepository.findAllByEncargado(idUsuario);
            List<SensorDTO> sensorDTOList = new ArrayList<>();
            String ubicacion = "";
            for(Estacion estacion : estaciones){
                List<Sensor> sensors = sensorRepository.findByIdEstacion(estacion.getId());
                for(Sensor sensor : sensors){
                    SensorDTO sensorDTO = new SensorDTO();
                    sensorDTO.setId(sensor.getId());
                    sensorDTO.setIdEstacion(sensor.getIdEstacion());
                    sensorDTO.setNombre(sensor.getNombre());
                    sensorDTO.setDescripcion(sensor.getDescripcion());
                    sensorDTO.setConfig(sensor.isConfig());
                    if (sensor.getIdEstacion() != null) {
                        Optional<Estacion> estacion1 = estacionRepository.findById(sensor.getIdEstacion());
                        ubicacion = estacion1.map(value -> value.getCiudad() + " - " + value.getDepartamento()).orElse("No se encontro la ubicacion");
                        sensorDTO.setUbicacion(ubicacion);
                    }
                    sensorDTOList.add(sensorDTO);
                }
            }
            return ResponseEntity.ok(sensorDTOList);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
