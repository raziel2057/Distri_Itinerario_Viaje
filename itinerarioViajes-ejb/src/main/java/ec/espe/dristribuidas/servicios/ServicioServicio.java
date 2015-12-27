/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.ServicioDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Servicio;
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
public class ServicioServicio {
    @EJB
    private ServicioDAO servicioDAO;
    
    public List<Servicio> obtenerTodas(){
        return this.servicioDAO.findAll();
    }
    
    public Servicio obtenerPorID(Integer codigoServicio){
        return this.servicioDAO.findById(codigoServicio, false);
    }


    public void crearServicio(Servicio servicio) throws ValidacionException {
        this.servicioDAO.insert(servicio);
        this.servicioDAO.flush();
    }

    public void actualiarServicio(Servicio servicio){
        this.servicioDAO.update(servicio);
    }

    public void eliminarServicio(Integer codigoServicio) {
        try {
            Servicio servicioTmp = this.obtenerPorID(codigoServicio);
            this.servicioDAO.remove(servicioTmp);
        } catch (Exception e) {
            throw new ValidacionException("El lugar " + codigoServicio + " esta asociadado a otra tabla");
        }
    }
}
