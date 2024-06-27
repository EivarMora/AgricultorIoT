package iser.apiOrion.DTO;


import lombok.Data;

@Data
public class SensorDTO {

    private String id;
    private String idEstacion;
    private String nombre;
    private String descripcion;
    private boolean config;
    private String ubicacion;

}
