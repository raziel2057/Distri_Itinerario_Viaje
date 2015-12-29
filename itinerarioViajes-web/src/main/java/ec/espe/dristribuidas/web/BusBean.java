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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;
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
    @EJB
    private EmpresaServicio empresaServicio;
    private List<Bus> buses;
    private Bus bus;
    private Bus busSelected;
    private Empresa empresa;
    private String codigoEmpresa;
    
    ValidacionesInputBean validacion = new ValidacionesInputBean();
    private List<String> anios = new ArrayList<String>();;
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

    public List<String> getAnios() {
        return anios;
    }

    public void setAnios(List<String> anios) {
        this.anios = anios;
    }

    
    
     @PostConstruct
    public void inicializar(){
        //buses.clear();
        buses = busServicio.obtenerTodas();
        Empresa empresaTmp;
        for(int i=0; i< buses.size();i++)
        {
            empresaTmp = empresaServicio.obtenerPorID(buses.get(i).getCodigoEmpresa());
            
            try {
                //BeanUtils.copyProperties(this.buses.get(i).getEmpresa(), empresaTmp);
                this.buses.get(i).setEmpresa(empresaTmp);
            } catch (Exception e) {
                FacesContext context = FacesContext.getCurrentInstance(); 
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado",  e.getMessage()));
            }
            empresaTmp=null;
        }
        Calendar c = Calendar.getInstance();
        anios = new ArrayList<String>();
        Integer annio = c.get(Calendar.YEAR);
         System.out.println(annio+""+(annio-25));
        for(int i =annio; i>=(annio-25);i--)
        {
            anios.add(i+"");
        }
        
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
        
        try {
            BeanUtils.copyProperties(this.bus,this.busSelected);
            //this.empresa =
            
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
                this.empresa = empresaServicio.obtenerPorID(this.bus.getCodigoEmpresa());
                this.bus.setEmpresa(this.empresa);
                //this.buses.add(0,this.bus);
                this.inicializar();
                Collections.reverse(this.buses);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el bus: "+this.bus.getCodigo(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnModificar()){
            try {
                this.busServicio.actualiarBus(this.bus);
                BeanUtils.copyProperties(this.busSelected, this.bus);
                this.inicializar();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo el bus: "+this.bus.getCodigo(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnEliminar()){
            try {
                this.busServicio.eliminarBus(this.bus.getCodigo());
                //this.buses.remove(this.bus);
                this.inicializar();
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
    
    public void validateCodigo() {

        String resultado = validacion.validateTextoLetrasNumeros(bus.getCodigo(), 7);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }
    
    public void validateMarca() {

        String resultado = validacion.validateTextoLetrasNumerosCaracteresEspeciales(bus.getMarca(), 30);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }
    
    public void validateModelo() {

        String resultado = validacion.validateTextoLetrasNumerosCaracteresEspeciales(bus.getModelo(), 30);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }
}
