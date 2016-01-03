/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.DetalleItinerarioDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.DetalleItinerario;
import ec.espe.dristribuidas.modelo.DetalleItinerarioPK;
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
public class DetalleItinerarioServicio {
    @EJB
    private DetalleItinerarioDAO detalleItinerarioDAO;
    
    public List<DetalleItinerario> obtenerTodas() {
        return this.detalleItinerarioDAO.findAll();
    }

    public DetalleItinerario obtenerPorID(DetalleItinerarioPK detalleItinerarioPK) {
        return this.detalleItinerarioDAO.findById(detalleItinerarioPK, false);
    }

    public void crearDetalleItinerario(DetalleItinerario detalleItinerario) throws ValidacionException {
        this.detalleItinerarioDAO.insert(detalleItinerario);
        this.detalleItinerarioDAO.flush();
    }

    public void actualiarDetalleItinerario(DetalleItinerario detalleItinerario) {
        this.detalleItinerarioDAO.update(detalleItinerario);
    }

    public void eliminarDetalleItinerario(DetalleItinerarioPK detalleItinerarioPK) {
        try {
            DetalleItinerario detalleItinerarioTmp = this.obtenerPorID(detalleItinerarioPK);
            this.detalleItinerarioDAO.remove(detalleItinerarioTmp);
        } catch (Exception e) {
            throw new ValidacionException("El Detalle de Itinerario " + detalleItinerarioPK + " esta asociadado a otra tabla");
        }
    }
}
