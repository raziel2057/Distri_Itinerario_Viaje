/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.reportes;

import java.util.List;

/**
 *
 * @author ale
 */
public class ModeloItinerario {
    
    //Cliente
    private String nombre;
    private String identificacion;
    private String direccion;
    private String telefono;
    
    private Integer codigoItinerario;
    
    
    private List<ModeloDetalleItinerario> boletos; 

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<ModeloDetalleItinerario> getBoletos() {
        return boletos;
    }

    public void setBoletos(List<ModeloDetalleItinerario> boletos) {
        this.boletos = boletos;
    }

    public Integer getCodigoItinerario() {
        return codigoItinerario;
    }

    public void setCodigoItinerario(Integer codigoItinerario) {
        this.codigoItinerario = codigoItinerario;
    }

    public ModeloItinerario(String nombre, String identificacion, String direccion, String telefono, Integer codigoItinerario, List<ModeloDetalleItinerario> boletos) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.codigoItinerario = codigoItinerario;
        this.boletos = boletos;
    }

    
    
    public ModeloItinerario() {
    }
    
}
