/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

/**
 *
 * @author ale
 */
import ec.espe.dristribuidas.common.dao.Order;
import ec.espe.dristribuidas.dao.ClienteDAO;
import ec.espe.dristribuidas.dao.LugarDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Cliente;
import ec.espe.dristribuidas.modelo.Lugar;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;


@LocalBean
@Stateless 
public class LugarServicio {
    
    @EJB
    private LugarDAO lugarDAO;
    
    public List<Lugar> obtenerTodas(){
        return this.lugarDAO.findAll();
    }
    
    public List<Lugar> obtenerTodasDesc(){
        List<Lugar> lugaresDesc = this.lugarDAO.findAll();
       Collections.reverse(lugaresDesc);
        return lugaresDesc; 
    }
    
    public Lugar obtenerPorID(Integer codigoLugar){
        return this.lugarDAO.findById(codigoLugar, false);
    }


    public void crearLugar(Lugar lugar) throws ValidacionException {
        this.lugarDAO.insert(lugar);
        this.lugarDAO.flush();
    }

    public void actualiarLugar(Lugar lugar){
        this.lugarDAO.update(lugar);
    }

    public void eliminarLugar(Integer codigoLugar) {
        try {
            Lugar lugarTmp = this.obtenerPorID(codigoLugar);
            this.lugarDAO.remove(lugarTmp);
        } catch (Exception e) {
            throw new ValidacionException("El lugar " + codigoLugar + " esta asociadado a otra tabla");
        }
    }
    
    
    
}
