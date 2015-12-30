/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.FacturaDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Factura;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author ale
 */
@LocalBean
@Stateless
public class FacturaServicio {

    @EJB
    private FacturaDAO facturaDAO;

    public List<Factura> obtenerTodas() {
        return this.facturaDAO.findAll();
    }

    public List<Factura> obtenerTodasPorIDCliente(Integer codigoCliente) {

        List<Factura> facturasTemp;
        List<Factura> facturasTodas;

        facturasTodas = obtenerTodas();
        facturasTemp = new ArrayList<Factura>();
        
        for (Factura facturasToda : facturasTodas) {
            if (Objects.equals(facturasToda.getCodigoCliente(), codigoCliente)) {
                facturasTemp.add(facturasToda);
            }
        }
        return facturasTemp;
    }

    public Factura obtenerPorID(Integer codigoFactura) {
        return this.facturaDAO.findById(codigoFactura, false);
    }

    public void crearFactura(Factura factura) throws ValidacionException {

        this.facturaDAO.insert(factura);
        this.facturaDAO.flush();
    }

    public void actualiarFactura(Factura factura) {
        this.facturaDAO.update(factura);
    }

    public void eliminarFactura(Integer codigoFactura) {
        try {
            Factura facturaTmp = this.obtenerPorID(codigoFactura);
            this.facturaDAO.remove(facturaTmp);
        } catch (Exception e) {
            throw new ValidacionException("El cliente " + codigoFactura + " esta asociada otra tabla");
        }
    }
}
