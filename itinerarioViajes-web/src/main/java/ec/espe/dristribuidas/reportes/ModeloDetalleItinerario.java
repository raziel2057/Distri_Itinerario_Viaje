/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.reportes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ale
 */
public class ModeloDetalleItinerario {
    
    //Detalle de boleto
    /*
    Nombre lugar de salida
    Nombre de lugar de llegada
    Fecha de salida
    Fecha de llegda
    Boleto No
    Bus
    Empresa
    Asiento
    Costo
    */
    
    private String nombreRuta;
    private java.util.Date fechaSalida;
    private java.util.Date fechaLlegada;
    private Integer codigoBoleto;
    private String codigoBus;
    private String nombreEmpresa;
    private String nombreAsiento;
    private BigDecimal costo;

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public Integer getCodigoBoleto() {
        return codigoBoleto;
    }

    public void setCodigoBoleto(Integer codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }

    public String getCodigoBus() {
        return codigoBus;
    }

    public void setCodigoBus(String codigoBus) {
        this.codigoBus = codigoBus;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreAsiento() {
        return nombreAsiento;
    }

    public void setNombreAsiento(String nombreAsiento) {
        this.nombreAsiento = nombreAsiento;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public ModeloDetalleItinerario(String nombreRuta, Date fechaSalida, Date fechaLlegada, Integer codigoBoleto, String codigoBus, String nombreEmpresa, String nombreAsiento, BigDecimal costo) {
        this.nombreRuta = nombreRuta;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.codigoBoleto = codigoBoleto;
        this.codigoBus = codigoBus;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreAsiento = nombreAsiento;
        this.costo = costo;
    }

    public ModeloDetalleItinerario() {
    }  
    
    
}
