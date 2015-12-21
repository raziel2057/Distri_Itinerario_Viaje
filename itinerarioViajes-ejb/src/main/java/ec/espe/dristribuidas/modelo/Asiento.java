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
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Alejandra Ponce
 */
@Entity
@Table(name = "G6_ASIENTO")
public class Asiento implements Serializable {
    
    @Id
    @SequenceGenerator(name = "G6_ASIENTO_SECUENCIA1", sequenceName = "G6_ASIENTO_SECUENCIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "G6_ASIENTO_SECUENCIA1")
    @Column(name = "CODIGO_ASIENTO", nullable = false)
    private Integer codigoAsiento;
    
    @Column(name = "CODIGO_BUS", nullable = false)
    private String codigoBus;
    
    @JoinColumn(name = "CODIGO_BUS", referencedColumnName = "CODIGO_BUS", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bus bus;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "COSTO", nullable = false)
    private BigDecimal costo;
    
    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public Integer getCodigoAsiento() {
        return codigoAsiento;
    }

    public void setCodigoAsiento(Integer codigoAsiento) {
        this.codigoAsiento = codigoAsiento;
    }

    public String getCodigoBus() {
        return codigoBus;
    }

    public void setCodigoBus(String codigoBus) {
        this.codigoBus = codigoBus;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.codigoAsiento);
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
        final Asiento other = (Asiento) obj;
        if (!Objects.equals(this.codigoAsiento, other.codigoAsiento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Asiento{" + "codigoAsiento=" + codigoAsiento + ", codigoBus=" + codigoBus + ", nombre=" + nombre + ", costo=" + costo + ", cantidad=" + cantidad + '}';
    }
    
    
    
    
}
