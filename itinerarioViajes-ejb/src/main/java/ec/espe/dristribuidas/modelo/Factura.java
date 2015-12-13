/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAUL
 */
@Entity
@Table(name = "G6_FACTURA")
public class Factura implements Serializable{
    @Id
    @Column(name = "CODIGO_FACTURA", nullable = false)
    private Integer codigo;
    
    @Column(name = "CODIGO_CLIENTE", nullable = false)
    private Integer codigoCliente;
    
    @JoinColumn(name = "CODIGO_CLIENTE", referencedColumnName = "CODIGO_CLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_EMISION", nullable = false)
    private Date fechaEmision;
    
    @Column(name = "COSTO_TOTAL", nullable = false)
    private BigDecimal costoTotal;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Factura other = (Factura) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Factura{" + "codigo=" + codigo + ", codigoCliente=" + codigoCliente + ", fechaEmision=" + fechaEmision + ", costoTotal=" + costoTotal + '}';
    }
    
    
}
