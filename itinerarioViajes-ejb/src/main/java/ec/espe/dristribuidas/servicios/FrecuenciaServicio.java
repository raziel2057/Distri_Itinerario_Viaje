/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.FrecuenciaDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Frecuencia;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author ale
 */

@LocalBean
@Stateless
public class FrecuenciaServicio {

    @EJB
    private FrecuenciaDAO frecuenciaDAO;

    public List<Frecuencia> obtenerTodas() {
        return this.frecuenciaDAO.findAll();
    }

    public Frecuencia obtenerPorID(Integer codigoFrecuencia) {
        return this.frecuenciaDAO.findById(codigoFrecuencia, false);
    }

    public void crearFrencuencia(Frecuencia frecuencia) throws ValidacionException {
        this.frecuenciaDAO.insert(frecuencia);
        this.frecuenciaDAO.flush();
    }

    public void actualiarFrecuencia(Frecuencia frecuencia) {
        this.frecuenciaDAO.update(frecuencia);
    }

    public void eliminarFrecuencia(Integer codigoFrecuencia) {
        try {
            Frecuencia frecuenciaTmp = this.obtenerPorID(codigoFrecuencia);
            this.frecuenciaDAO.remove(frecuenciaTmp);
        } catch (Exception e) {
            throw new ValidacionException("La frecuencia " + codigoFrecuencia + " esta asociadado a otra tabla");
        }
    }
}
