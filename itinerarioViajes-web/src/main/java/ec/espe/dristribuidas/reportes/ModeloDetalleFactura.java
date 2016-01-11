/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.reportes;

import java.math.BigDecimal;

/**
 *
 * @author ale
 */
public class ModeloDetalleFactura {
    
    //Detalles de la Factura
    
    private String descripcionServicio;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private BigDecimal precioTotal;

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public ModeloDetalleFactura(String descripcionServicio, BigDecimal precioUnitario, Integer cantidad, BigDecimal precioTotal) {
        this.descripcionServicio = descripcionServicio;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public ModeloDetalleFactura() {
    }
    
    
    
}
