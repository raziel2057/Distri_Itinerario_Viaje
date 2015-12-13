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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Alejandra Ponce
 */
@Entity
@Table(name = "G6_BOLETO")
public class Boleto implements Serializable {
    
    @Id
    @Column(name = "CODIGO_BOLETO", nullable = false)
    private Integer codigo;
    
    @Column(name = "CODIGO_ASIENTO", nullable = false)
    private Integer codigoAsiento;
    
    //No estoy segura que se deba manejar de esta manera FK
    @JoinColumn(name = "CODIGO_ASIENTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Asiento asiento;
    
    @Column(name = "CODIGO_FRECUENCIA", nullable = false)
    private Integer codigoFrecuencia;
    
    @JoinColumn(name = "CODIGO_FRECUENCIA", referencedColumnName = "CODIGO_FRECUENCIA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Frecuencia frecuencia;
    
    
    @Column(name = "COSTO", nullable = false)
    private BigDecimal costo;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoAsiento() {
        return codigoAsiento;
    }

    public void setCodigoAsiento(Integer codigoAsiento) {
        this.codigoAsiento = codigoAsiento;
    }

    public Integer getCodigoFrecuencia() {
        return codigoFrecuencia;
    }

    public void setCodigoFrecuencia(Integer codigoFrecuencia) {
        this.codigoFrecuencia = codigoFrecuencia;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.codigo);
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
        final Boleto other = (Boleto) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Boleto{" + "codigo=" + codigo + ", codigoAsiento=" + codigoAsiento  + ", codigoFrecuencia=" + codigoFrecuencia + ", costo=" + costo + '}';
    }
    
    
    
}
