/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author RAUL
 */
public class DetalleHospedajePK implements Serializable{
    protected Integer codigoHospedaje;
    protected Integer codigoItinerario;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.codigoHospedaje);
        hash = 37 * hash + Objects.hashCode(this.codigoItinerario);
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
        final DetalleHospedajePK other = (DetalleHospedajePK) obj;
        if (!Objects.equals(this.codigoHospedaje, other.codigoHospedaje)) {
            return false;
        }
        if (!Objects.equals(this.codigoItinerario, other.codigoItinerario)) {
            return false;
        }
        return true;
    }
    
    
   
    
}
