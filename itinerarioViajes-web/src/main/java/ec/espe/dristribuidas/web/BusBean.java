/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Bus;
import ec.espe.dristribuidas.modelo.Empresa;
import ec.espe.dristribuidas.servicios.BusServicio;
import ec.espe.dristribuidas.servicios.EmpresaServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class BusBean extends BaseBean implements Serializable{
    @EJB
    private BusServicio busServicio;

    private List<Bus> buses;
    private Bus bus;
    private Bus busSelected;
    private Empresa empresaSelected;
    private Empresa empresa;
    private String codigoEmpresa;

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Bus getBusSelected() {
        return busSelected;
    }

    public void setBusSelected(Bus busSelected) {
        this.busSelected = busSelected;
    }


    public Empresa getEmpresaSelected() {
        return empresaSelected;
    }

    public void setEmpresaSelected(Empresa empresaSelected) {
        this.empresaSelected = empresaSelected;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    
    
     @PostConstruct
    public void inicializar(){
        buses = busServicio.obtenerTodas();
        
    }
    
    @Override
    public void nuevo() {
        super.nuevo();
        this.bus = new Bus();
        this.empresa = new Empresa();
    }
    
    
    @Override
    public void modificar() {

        super.modificar();
        this.bus = new Bus();
        this.empresa = new Empresa();
        try {
            BeanUtils.copyProperties(this.bus,this.busSelected);
            
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance(); 
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado",  e.getMessage()));
        }
    }
    
    @Override
    public void eliminar() {
        super.eliminar();
        this.bus = new Bus();
        try {
            BeanUtils.copyProperties(this.bus, this.busSelected);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance(); 
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado",  e.getMessage()));
        }
    }
    
    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance(); 
        if (super.isEnNuevo()) {
            try {
                //Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");
                //this.bus.setCodigoEmpresa(this.codigoEmpresa);
                this.busServicio.crearBus(this.bus);
                this.buses.add(0,this.bus);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el bus: "+this.bus.getCodigo(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnModificar()){
            try {
                this.busServicio.actualiarBus(this.bus);
                BeanUtils.copyProperties(this.busSelected, this.bus);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo el bus: "+this.bus.getCodigo(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnEliminar()){
            try {
                this.busServicio.eliminarBus(this.bus.getCodigo());
                this.buses.remove(this.bus);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino el bus: "+this.bus.getCodigo(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        }
        this.cancelar();
    }
    
    public void cancelar() {
        super.reset();
        this.bus = null;
        this.busSelected = null;
    }
}
