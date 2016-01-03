/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.BoletoDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Boleto;
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
public class BoletoServicio {
    @EJB
    private BoletoDAO boletoDAO;
    
    public List<Boleto> obtenerTodas(){
        return this.boletoDAO.findAll();
        
    }
  
    public Boleto obtenerPorID(Integer codigoBoleto){
        return this.boletoDAO.findById(codigoBoleto, false);
    }


    public void crearBoleto(Boleto boleto) throws ValidacionException {

            this.boletoDAO.insert(boleto);
               
    }
    
    public void liberar()
    {
        this.boletoDAO.flush(); 
    }

    public void actualiarBoleto(Boleto boleto){
        this.boletoDAO.update(boleto);
    }

    public void eliminarBoleto(Integer codigoBoleto) {
        try {
            Boleto boletoTmp = this.obtenerPorID(codigoBoleto);
            this.boletoDAO.remove(boletoTmp);
        } catch (Exception e) {
            throw new ValidacionException("El Asiento " + codigoBoleto + " esta asociadado a otra tabla");
        }
    }
}
