/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.DetalleFacturaDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.DetalleFactura;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author ale
 */

@LocalBean
@Stateless
public class DetalleFacturaServicio {
    
    @EJB
    private DetalleFacturaDAO detalleFacturaDAO;
    
    public List<DetalleFactura> obtenerTodas() {
        return this.detalleFacturaDAO.findAll();
    }

    public List<DetalleFactura> obtenerTodasPorIDFactura(Integer codigoFactura) {

        List<DetalleFactura> detallesFacturasTemp;
        List<DetalleFactura> detallesFacturasTodas;

        detallesFacturasTodas = obtenerTodas();
        detallesFacturasTemp = new ArrayList<DetalleFactura>();
        
        for (DetalleFactura detallesFacturasToda : detallesFacturasTodas) {
            if (Objects.equals(detallesFacturasToda.getCodigoFactura(), codigoFactura)) {
                detallesFacturasTemp.add(detallesFacturasToda);
            }
        }
        return detallesFacturasTemp;
    }

    public DetalleFactura obtenerPorID(Integer codigoDetalleFactura) {
        return this.detalleFacturaDAO.findById(codigoDetalleFactura, false);
    }

    public void crearDetalleFactura(DetalleFactura detalleFactura) throws ValidacionException {

        this.detalleFacturaDAO.insert(detalleFactura);
        this.detalleFacturaDAO.flush();
    }

    public void actualiarDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFacturaDAO.update(detalleFactura);
    }

    public void eliminarDetalleFactura(Integer codigoDetalleFactura) {
        try {
            DetalleFactura detalleFacturaTmp = this.obtenerPorID(codigoDetalleFactura);
            this.detalleFacturaDAO.remove(detalleFacturaTmp);
        } catch (Exception e) {
            throw new ValidacionException("El detalle de factura " + codigoDetalleFactura + " esta asociada otra tabla");
        }
    }
    
    
}
