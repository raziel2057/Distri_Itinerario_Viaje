/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.BusDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Bus;
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
public class BusServicio {
    @EJB
    private BusDAO busDao;
    
    public List<Bus> obtenerTodas(){
        return this.busDao.findAll();
    }
    
    public Bus obtenerPorID(String codigoBus){
        return this.busDao.findById(codigoBus, false);
    }


    public void crearBus(Bus bus) throws ValidacionException {
        this.busDao.insert(bus);
        this.busDao.flush();
    }

    public void actualiarBus(Bus bus){
        this.busDao.update(bus);
    }

    public void eliminarBus(String codigoBus) {
        try {
            Bus busTmp = this.obtenerPorID(codigoBus);
            this.busDao.remove(busTmp);
        } catch (Exception e) {
            throw new ValidacionException("El lugar " + codigoBus + " esta asociadado a otra tabla");
        }
    }
}
