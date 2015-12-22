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
@Table(name = "G6_SERVICIO")
public class Servicio implements Serializable {
    
    @Id
    @SequenceGenerator(name = "G6_SERVICIO_SECUENCIA1", sequenceName = "G6_SERVICIO_SECUENCIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "G6_SERVICIO_SECUENCIA1")
    @Column(name = "CODIGO_SERVICIO", nullable = false)
    private Integer codigo;
    
    @Column(name = "CODIGO_BUS", nullable = false)
    private String codigoBus;
    
    @JoinColumn(name = "CODIGO_BUS", referencedColumnName = "CODIGO_BUS", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bus bus;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "COSTO", nullable = false)
    private BigDecimal costo;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.codigo);
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
        final Servicio other = (Servicio) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Servicio{" + "codigoServicio=" + codigo + ", codigoBus=" + codigoBus + ", nombre=" + nombre + ", costo=" + costo + '}';
    }
    
    
    
    
    
}
