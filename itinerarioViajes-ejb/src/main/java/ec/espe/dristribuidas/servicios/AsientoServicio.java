/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.AsientoDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Asiento;
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
public class AsientoServicio {
    @EJB
    private AsientoDAO asientoDAO;
    
    public List<Asiento> obtenerTodas(){
        return this.asientoDAO.findAll();
        
    }
    public List<Asiento> obtenerFiltrado(Asiento asiento){
        return this.asientoDAO.findO(asiento);
        
    }
    
    
    
    public Asiento obtenerPorID(Integer codigoAsiento){
        return this.asientoDAO.findById(codigoAsiento, false);
    }


    public void crearAsiento(Asiento asiento) throws ValidacionException {

            this.asientoDAO.insert(asiento);
               
    }
    
    public void liberar()
    {
        this.asientoDAO.flush(); 
    }

    public void actualiarAsiento(Asiento asiento){
        this.asientoDAO.update(asiento);
    }

    public void eliminarAsiento(Integer codigoAsiento) {
        try {
            Asiento asientoTmp = this.obtenerPorID(codigoAsiento);
            this.asientoDAO.remove(asientoTmp);
        } catch (Exception e) {
            throw new ValidacionException("El lugar " + codigoAsiento + " esta asociadado a otra tabla");
        }
    }
}
