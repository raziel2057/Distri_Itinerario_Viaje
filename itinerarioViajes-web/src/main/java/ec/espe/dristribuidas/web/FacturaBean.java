/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Cliente;
import ec.espe.dristribuidas.modelo.DetalleFactura;
import ec.espe.dristribuidas.modelo.Factura;
import ec.espe.dristribuidas.servicios.DetalleFacturaServicio;
import ec.espe.dristribuidas.servicios.FacturaServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author ale
 */
@ViewScoped
@ManagedBean
public class FacturaBean extends BaseBean implements Serializable {

    @EJB
    private FacturaServicio facturaServicio;
    private List<Factura> facturas; //Todas las facturas de la table Facturas
    private List<Factura> facturasCliente; //Todas las facturas asociadas a un cliente
    private Factura factura;
    private Factura facturaSelected;

    @EJB
    private DetalleFacturaServicio detalleFacturaServicio;
    private List<DetalleFactura> detallesFactura; //Todos los detalles de una factura
    
    ValidacionesInputBean validacion = new ValidacionesInputBean();

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean datosLogin;
    
    
    private Cliente cliente;

    public LoginBean getDatosLogin() {
        return datosLogin;
    }

    public void setDatosLogin(LoginBean datosLogin) {
        this.datosLogin = datosLogin;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Factura getFacturaSelected() {
        return facturaSelected;
    }

    public void setFacturaSelected(Factura facturaSelected) {
        this.facturaSelected = facturaSelected;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Factura> getFacturasCliente() {
        return facturasCliente;
    }

    public void setFacturasCliente(List<Factura> facturasCliente) {
        this.facturasCliente = facturasCliente;
    }

    public List<DetalleFactura> getDetallesFactura() {
        return detallesFactura;
    }

    public void setDetallesFactura(List<DetalleFactura> detallesFactura) {
        this.detallesFactura = detallesFactura;
    }
    
    

    @PostConstruct
    public void inicializar() {
        
        cliente= new Cliente();
        try {
            BeanUtils.copyProperties(this.cliente, this.getDatosLogin().getCliente());

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
        
        facturas = facturaServicio.obtenerTodas();
        facturasCliente=facturaServicio.obtenerTodasPorIDCliente(this.cliente.getCodigo());
  
    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.factura = new Factura(); 
        //Para la nueva factura aqui se deber[ia cargar el factura.cliente con this.cliente
    }

    @Override
    public void modificar() {

        super.modificar();
        this.factura = new Factura();
        try {
            BeanUtils.copyProperties(this.factura, this.facturaSelected);

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    @Override
    public void eliminar() {
        super.eliminar();
        this.factura = new Factura();
        try {
            BeanUtils.copyProperties(this.factura, this.facturaSelected);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (super.isEnNuevo()) {
            if (validarFactura()) {
                try {
                    //Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");

                    this.facturaServicio.crearFactura(this.factura);
                    this.facturas.add(0, this.factura);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la factura ", null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede ingresar la factura ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnModificar()) {
            if (validarFactura()) {
                try {
                    this.facturaServicio.actualiarFactura(this.factura);
                    BeanUtils.copyProperties(this.facturaSelected, this.factura);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo la factura ", null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede actualizar la factura ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnEliminar()) {
            try {
                this.facturaServicio.eliminarFactura(this.factura.getCodigo());
                this.facturas.remove(this.factura);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino la factura ", null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }
        this.cancelar();
    }

    public void cancelar() {
        super.reset();
        this.factura = null;
        this.facturaSelected = null;
    }
    
    public void mostrarDetallesFactura(){
        detallesFactura=detalleFacturaServicio.obtenerTodasPorIDFactura(this.facturaSelected.getCodigo());        
        
    }
    

    public void validateCostoTotal() {

        String resultado = validacion.validateNumeroDecimal(this.factura.getCostoTotal().toString(), 7);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public boolean validarFactura() {

        if (validacion.validateNumeroDecimal(this.factura.getCostoTotal().toString(), 7) == "se") {
            return true;
        } else {
            return false;
        }

    }

}
