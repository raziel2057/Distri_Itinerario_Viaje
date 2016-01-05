/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.utils;

import java.math.BigDecimal;

/**
 *
 * @author RAUL
 */
public class ItineararioString {
    private Integer codigo;
    private String descripcion;
    private BigDecimal costo;
    private BigDecimal distancia;
    private String tiempo;
    private Integer numeroConexiones;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getDistancia() {
        return distancia;
    }

    public void setDistancia(BigDecimal distancia) {
        this.distancia = distancia;
    }

    public Integer getNumeroConexiones() {
        return numeroConexiones;
    }

    public void setNumeroConexiones(Integer numeroConexiones) {
        this.numeroConexiones = numeroConexiones;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
    
    
}
