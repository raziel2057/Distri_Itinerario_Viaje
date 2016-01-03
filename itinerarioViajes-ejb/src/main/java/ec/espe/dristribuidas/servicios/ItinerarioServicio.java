/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.ItinerarioDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Itinerario;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author RAUL
 */
@LocalBean
@Stateless
public class ItinerarioServicio {
    @EJB
    private ItinerarioDAO itinerarioDAO;
    
    public List<Itinerario> obtenerTodas() {
        return this.itinerarioDAO.findAll();
    }

    public Itinerario obtenerPorID(Integer codigoItinerario) {
        return this.itinerarioDAO.findById(codigoItinerario, false);
    }

    public void crearItinerario(Itinerario itinerario) throws ValidacionException {
        this.itinerarioDAO.insert(itinerario);
        this.itinerarioDAO.flush();
    }

    public void actualiarItinerario(Itinerario itinerario) {
        this.itinerarioDAO.update(itinerario);
    }

    public void eliminarItinerario(Integer codigoItinerario) {
        try {
            Itinerario itinerarioTmp = this.obtenerPorID(codigoItinerario);
            this.itinerarioDAO.remove(itinerarioTmp);
        } catch (Exception e) {
            throw new ValidacionException("El Itineario " + codigoItinerario + " esta asociadado a otra tabla");
        }
    }
    
}
