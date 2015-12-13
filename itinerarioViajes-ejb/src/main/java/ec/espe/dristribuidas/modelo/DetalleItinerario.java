/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAUL
 */
@Entity
@Table(name = "G6_DETALLE_ITINERARIO")
@IdClass(DetalleItinerarioPK.class)
public class DetalleItinerario implements Serializable {
    @Id
    @Column(name = "CODIGO_ITINERARIO", nullable = false)
    private Integer codigoItinerario;
    
    @JoinColumn(name = "CODIGO_ITINERARIO", referencedColumnName = "CODIGO_ITINERARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Itinerario itinerario;
    
    @Id
    @Column(name = "CODIGO_BOLETO", nullable = false)
    private Integer codigoBoleto;
    
    @JoinColumn(name = "CODIGO_BOLETO", referencedColumnName = "CODIGO_BOLETO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Boleto boleto;
    
    @Column(name = "TIEMPO_ESTADIA_HORAS", nullable = false)
    private Integer tiempoEstadiaHoras;

    public Integer getCodigoItinerario() {
        return codigoItinerario;
    }

    public void setCodigoItinerario(Integer codigoItinerario) {
        this.codigoItinerario = codigoItinerario;
    }

    public Integer getCodigoBoleto() {
        return codigoBoleto;
    }

    public void setCodigoBoleto(Integer codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }

    public Integer getTiempoEstadiaHoras() {
        return tiempoEstadiaHoras;
    }

    public void setTiempoEstadiaHoras(Integer tiempoEstadiaHoras) {
        this.tiempoEstadiaHoras = tiempoEstadiaHoras;
    }

    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.codigoItinerario);
        hash = 31 * hash + Objects.hashCode(this.codigoBoleto);
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
        final DetalleItinerario other = (DetalleItinerario) obj;
        if (!Objects.equals(this.codigoItinerario, other.codigoItinerario)) {
            return false;
        }
        if (!Objects.equals(this.codigoBoleto, other.codigoBoleto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DetalleItinerario{" + "codigoItinerario=" + codigoItinerario + ", codigoBoleto=" + codigoBoleto + ", tiempoEstadiaHoras=" + tiempoEstadiaHoras + '}';
    }
    
    
}
