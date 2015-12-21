/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Alejandra Ponce
 */

@Entity
@Table(name = "G6_RUTA")
public class Ruta implements Serializable {
    
    @Id
    @SequenceGenerator(name = "G6_RUTA_SECUENCIA1", sequenceName = "G6_RUTA_SECUENCIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "G6_RUTA_SECUENCIA1")
    @Column(name = "CODIGO_RUTA", nullable = false)
    private Integer codigo;
    
    @Column(name = "CODIGO_LUGAR_SALIDA", nullable = false)
    private Integer codigoLugarSalida;
    
    @JoinColumn(name = "CODIGO_LUGAR_SALIDA", referencedColumnName = "CODIGO_LUGAR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lugar lugarSalida;
    
    @Column(name = "CODIGO_LUGAR_LLEGADA", nullable = false)
    private Integer codigoLugarLlegada;
    
    @JoinColumn(name = "CODIGO_LUGAR_LLEGADA", referencedColumnName = "CODIGO_LUGAR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lugar lugarLlegada;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "COSTO", nullable = false)
    private BigDecimal costo;
    
    @Column(name = "TIEMPO_HORAS", nullable = false)
    private BigDecimal tiempoHoras;

    public Integer getCodigoRuta() {
        return codigo;
    }

    public void setCodigoRuta(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoLugarSalida() {
        return codigoLugarSalida;
    }

    public void setCodigoLugarSalida(Integer codigoLugarSalida) {
        this.codigoLugarSalida = codigoLugarSalida;
    }

    public Integer getCodigoLugarLlegada() {
        return codigoLugarLlegada;
    }

    public void setCodigoLugarLlegada(Integer codigoLugarLlegada) {
        this.codigoLugarLlegada = codigoLugarLlegada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getTiempoHoras() {
        return tiempoHoras;
    }

    public void setTiempoHoras(BigDecimal tiempoHoras) {
        this.tiempoHoras = tiempoHoras;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ruta other = (Ruta) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ruta{" + "codigo=" + codigo + ", codigoLugarSalida=" + codigoLugarSalida + ", codigoLugarLlegada=" + codigoLugarLlegada + ", nombre=" + nombre + ", costo=" + costo + ", tiempoHoras=" + tiempoHoras + '}';
    } 
    
    
}
