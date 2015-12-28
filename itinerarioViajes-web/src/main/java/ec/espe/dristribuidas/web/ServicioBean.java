/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Servicio;
import ec.espe.dristribuidas.servicios.ServicioServicio;
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
public class ServicioBean extends BaseBean implements Serializable {
    @EJB
    private ServicioServicio servicioServicio;
    private List<Servicio> servicios;
    private Servicio servicio;
    private Servicio servicioSelected;

    public ServicioServicio getServicioServicio() {
        return servicioServicio;
    }

    public void setServicioServicio(ServicioServicio servicioServicio) {
        this.servicioServicio = servicioServicio;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Servicio getServicioSelected() {
        return servicioSelected;
    }

    public void setServicioSelected(Servicio servicioSelected) {
        this.servicioSelected = servicioSelected;
    }
    
    
    @PostConstruct
    public void inicializar(){
        servicios = servicioServicio.obtenerTodas();
        
    }
    
    @Override
    public void nuevo() {
        super.nuevo();
        this.servicio = new Servicio();
    }
    
    
    @Override
    public void modificar() {

        super.modificar();
        this.servicio = new Servicio();
        try {
            BeanUtils.copyProperties(this.servicio,this.servicioSelected);
            
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance(); 
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado",  e.getMessage()));
        }
    }
    
    @Override
    public void eliminar() {
        super.eliminar();
        this.servicio = new Servicio();
        try {
            BeanUtils.copyProperties(this.servicio, this.servicioSelected);
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
                
                this.servicioServicio.crearServicio(this.servicio);
                this.servicios.add(0,this.servicio);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el servicio: "+this.servicio.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnModificar()){
            try {
                this.servicioServicio.actualiarServicio(this.servicio);
                BeanUtils.copyProperties(this.servicioSelected, this.servicio);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo el servicio: "+this.servicio.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnEliminar()){
            try {
                this.servicioServicio.eliminarServicio(this.servicio.getCodigo());
                this.servicios.remove(this.servicio);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino el servicio: "+this.servicio.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        }
        this.cancelar();
    }
    
    public void cancelar() {
        super.reset();
        this.servicio = null;
        this.servicioSelected = null;
    }
    
}
