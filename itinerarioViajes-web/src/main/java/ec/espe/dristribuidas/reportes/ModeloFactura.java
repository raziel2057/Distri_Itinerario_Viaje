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
public class ModeloFactura {
 

    //Cliente
    private String nombre;
    private String identificacion;
    private String direccion;
    private String telefono;
    
    //Factura
    private Integer codigoFactura;
    private Date fechaEmision;
    private BigDecimal costoTotal;
    
    private List<ModeloDetalleFactura> detalle; 

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

    public Integer getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(Integer codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public List<ModeloDetalleFactura> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<ModeloDetalleFactura> detalle) {
        this.detalle = detalle;
    }

    public ModeloFactura(String nombre, String identificacion, String direccion, String telefono, Integer codigoFactura, Date fechaEmision, BigDecimal costoTotal, List<ModeloDetalleFactura> detalle) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.codigoFactura = codigoFactura;
        this.fechaEmision = fechaEmision;
        this.costoTotal = costoTotal;
        this.detalle = detalle;
    }

    public ModeloFactura() {
    }
    
    
     
}
