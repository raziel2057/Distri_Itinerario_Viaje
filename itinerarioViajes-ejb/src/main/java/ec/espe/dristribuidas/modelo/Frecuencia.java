/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Alejandra Ponce
 */

@Entity
@Table(name = "G6_FRECUENCIA")
public class Frecuencia implements Serializable {
    
    @Id
    @SequenceGenerator(name = "G6_FRECUENCIA_SECUENCIA1", sequenceName = "G6_FRECUENCIA_SECUENCIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "G6_FRECUENCIA_SECUENCIA1")
    @Column(name = "CODIGO_FRECUENCIA", nullable = false)
    private Integer codigo;
    
    @Column(name = "CODIGO_BUS", nullable = false)
    private String codigoBus;
    
    @JoinColumn(name = "CODIGO_BUS", referencedColumnName = "CODIGO_BUS", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bus bus;
    
    @Column(name = "CODIGO_RUTA", nullable = false)
    private Integer codigoRuta;
    
    @JoinColumn(name = "CODIGO_RUTA", referencedColumnName = "CODIGO_RUTA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ruta ruta;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_SALIDA", nullable = false)
    private java.util.Date fechaSalida;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_LLEGADA", nullable = false)
    private java.util.Date fechaLlegada;

    public Integer getCodigoFrecuencia() {
        return codigo;
    }

    public void setCodigoFrecuencia(Integer codigoFrecuencia) {
        this.codigo = codigoFrecuencia;
    }

    public String getCodigoBus() {
        return codigoBus;
    }

    public void setCodigoBus(String codigoBus) {
        this.codigoBus = codigoBus;
    }

    public Integer getCodigoRuta() {
        return codigoRuta;
    }

    public void setCodigoRuta(Integer codigoRuta) {
        this.codigoRuta = codigoRuta;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.codigo);
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
        final Frecuencia other = (Frecuencia) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Frecuencia{" + "codigo=" + codigo + ", codigoBus=" + codigoBus + ", codigoRuta=" + codigoRuta + ", fechaSalida=" + fechaSalida + ", fechaLlegada=" + fechaLlegada + '}';
    }
    
    
    
    
}
