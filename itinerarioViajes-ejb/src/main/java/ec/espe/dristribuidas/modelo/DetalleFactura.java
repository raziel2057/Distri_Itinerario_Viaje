/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAUL
 */
@Entity
@Table(name = "G6_DETALLE_FACTURA")
public class DetalleFactura implements Serializable{
    
    @Id
    @SequenceGenerator(name = "G6_DETALLE_FACTURA_SECUENCIA1", sequenceName = "G6_DETALLE_FACTURA_SECUENCIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "G6_DETALLE_FACTURA_SECUENCIA1")
    @Column(name = "CODIGO_DETALLE_FACTURA", nullable = false)
    private Integer codigoDetalleFactura;
    
    @Column(name = "CODIGO_FACTURA", nullable = false)
    private Integer codigoFactura;
    
    @JoinColumn(name = "CODIGO_FACTURA", referencedColumnName = "CODIGO_FACTURA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Factura factura;
    
    @Column(name = "DESCRIPCION_SERVICIO", nullable = false)
    private String descripcionServicio;
    
    @Column(name = "PRECIO_UNITARIO", nullable = false)
    private BigDecimal precioUnitario;
    
    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;
    
    @Column(name = "PRECIO_TOTAL", nullable = false)
    private BigDecimal precioTotal;

    public Integer getCodigoDetalleFactura() {
        return codigoDetalleFactura;
    }

    public void setCodigoDetalleFactura(Integer codigoDetalleFactura) {
        this.codigoDetalleFactura = codigoDetalleFactura;
    }
    
    public Integer getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(Integer codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

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

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.codigoDetalleFactura);
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
        final DetalleFactura other = (DetalleFactura) obj;
        if (!Objects.equals(this.codigoDetalleFactura, other.codigoDetalleFactura)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" + "codigoDetalleFactura=" + codigoDetalleFactura + ", codigoFactura=" + codigoFactura + ", factura=" + factura + ", descripcionServicio=" + descripcionServicio + ", precioUnitario=" + precioUnitario + ", cantidad=" + cantidad + ", precioTotal=" + precioTotal + '}';
    }
    
    
    
    
}
