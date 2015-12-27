/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.RutaDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Ruta;
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
public class RutaServicio {

    @EJB
    private RutaDAO rutaDAO;

    public List<Ruta> obtenerTodas() {
        return this.rutaDAO.findAll();
    }

    public Ruta obtenerPorID(Integer codigoRuta) {
        return this.rutaDAO.findById(codigoRuta, false);
    }

    public void crearRuta(Ruta ruta) throws ValidacionException {
        this.rutaDAO.insert(ruta);
    }

    public void actualiarRuta(Ruta ruta) {
        this.rutaDAO.update(ruta);
    }

    public void eliminarRuta(Integer codigoRuta) {
        try {
            Ruta rutaTmp = this.obtenerPorID(codigoRuta);
            this.rutaDAO.remove(rutaTmp);
        } catch (Exception e) {
            throw new ValidacionException("La ruta " + codigoRuta + " esta asociadado a otra tabla");
        }
    }

}
