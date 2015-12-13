/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAUL
 */
@Entity
@Table(name = "G6_DETALLE_HOSPEDAJE")
@IdClass(DetalleHospedajePK.class)
public class DetalleHospedaje implements Serializable{
    @Id
    @Column(name = "CODIGO_HOSPEDAJE", nullable = false)
    private Integer codigoHospedaje;
    
    @JoinColumn(name = "CODIGO_HOSPEDAJE", referencedColumnName = "CODIGO_HOSPEDAJE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Hospedaje hospedaje;
    
    @Id
    @Column(name = "CODIGO_ITINERARIO", nullable = false)
    private Integer codigoItinerario;
    
    @JoinColumn(name = "CODIGO_ITINERARIO", referencedColumnName = "CODIGO_ITINERARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Itinerario itinerario;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA", nullable = false)
    private Date fecha;

    public Integer getCodigoHospedaje() {
        return codigoHospedaje;
    }

    public void setCodigoHospedaje(Integer codigoHospedaje) {
        this.codigoHospedaje = codigoHospedaje;
    }

    public Integer getCodigoItinerario() {
        return codigoItinerario;
    }

    public void setCodigoItinerario(Integer codigoItinerario) {
        this.codigoItinerario = codigoItinerario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Hospedaje getHospedaje() {
        return hospedaje;
    }

    public void setHospedaje(Hospedaje hospedaje) {
        this.hospedaje = hospedaje;
    }

    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.codigoHospedaje);
        hash = 71 * hash + Objects.hashCode(this.codigoItinerario);
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
        final DetalleHospedaje other = (DetalleHospedaje) obj;
        if (!Objects.equals(this.codigoHospedaje, other.codigoHospedaje)) {
            return false;
        }
        if (!Objects.equals(this.codigoItinerario, other.codigoItinerario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DetalleHospedaje{" + "codigoHospedaje=" + codigoHospedaje + ", codigoItinerario=" + codigoItinerario + ", fecha=" + fecha + '}';
    }
    
    
}
