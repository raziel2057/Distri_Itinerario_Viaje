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
public class DetalleItinerarioPK implements Serializable {
    protected Integer codigoItinerario;
    protected Integer codigoBoleto;

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.codigoItinerario);
        hash = 41 * hash + Objects.hashCode(this.codigoBoleto);
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
        final DetalleItinerarioPK other = (DetalleItinerarioPK) obj;
        if (!Objects.equals(this.codigoItinerario, other.codigoItinerario)) {
            return false;
        }
        if (!Objects.equals(this.codigoBoleto, other.codigoBoleto)) {
            return false;
        }
        return true;
    }
    
    
}
