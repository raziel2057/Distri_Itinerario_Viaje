/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;



import ec.espe.dristribuidas.modelo.Asiento;
import ec.espe.dristribuidas.modelo.Bus;
import ec.espe.dristribuidas.modelo.Empresa;
import ec.espe.dristribuidas.servicios.AsientoServicio;
import ec.espe.dristribuidas.servicios.BusServicio;
import ec.espe.dristribuidas.servicios.EmpresaServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class AsientoBean extends BaseBean implements Serializable {
    @EJB
    private AsientoServicio asientoServicio;
    
    private List<Asiento> asientos;
    private List<Asiento> asientosPorBus;
    private Asiento asiento;
    private Asiento asientoSelected;
    private Integer numeroAsientos;
    private BigDecimal costo;
    private Integer numeroMaxAsientos;
    private List<Integer> listadoNumeroAsientos;
    @EJB
    private BusServicio busServicio;
    private List<Bus> buses;
    private List<Bus> busesPorEmpresa;
    private String codigoBus;
    
    @EJB
    private EmpresaServicio empresaServicio;
    private List<Empresa> empresas;
    private String codigoEmpresa;
    

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public List<Asiento> getAsientosPorBus() {
        return asientosPorBus;
    }

    public void setAsientosPorBus(List<Asiento> asientosPorBus) {
        this.asientosPorBus = asientosPorBus;
    }
    
    

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public Asiento getAsientoSelected() {
        return asientoSelected;
    }

    public void setAsientoSelected(Asiento asientoSelected) {
        this.asientoSelected = asientoSelected;
    }

    public Integer getNumeroAsientos() {
        return numeroAsientos;
    }

    public void setNumeroAsientos(Integer numeroAsientos) {
        this.numeroAsientos = numeroAsientos;
    }

    public String getCodigoBus() {
        return codigoBus;
    }

    public void setCodigoBus(String codigoBus) {
        this.codigoBus = codigoBus;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    public List<Bus> getBusesPorEmpresa() {
        return busesPorEmpresa;
    }

    public void setBusesPorEmpresa(List<Bus> busesPorEmpresa) {
        this.busesPorEmpresa = busesPorEmpresa;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public Integer getNumeroMaxAsientos() {
        return numeroMaxAsientos;
    }

    public void setNumeroMaxAsientos(Integer numeroMaxAsientos) {
        this.numeroMaxAsientos = numeroMaxAsientos;
    }

    public List<Integer> getListadoNumeroAsientos() {
        return listadoNumeroAsientos;
    }

    public void setListadoNumeroAsientos(List<Integer> listadoNumeroAsientos) {
        this.listadoNumeroAsientos = listadoNumeroAsientos;
    }
    
    
    
    @PostConstruct
    public void inicializar()
    {
        this.empresas = this.empresaServicio.obtenerTodas();
        
        this.cargarBuses();
        
        /*this.busesPorEmpresa = Lambda.select(this.buses, having(on(Bus.class).getCodigoEmpresa(),
                Matchers.equalTo("")));*/

        /*this.asientos=this.asientoServicio.obtenerTodas();
        /*this.asientosPorBus =  Lambda.select(this.asientos,having(on(Asiento.class).getCodigoBus(),
                equalTo(this.codigoBus)));*/
    }
    
    public void cargarBuses()
    {
        this.buses = this.busServicio.obtenerTodas();
        System.out.println("Cosigo empresa: "+this.codigoEmpresa);
        if(this.codigoEmpresa == null)
        {
            this.codigoEmpresa = this.empresas.get(0).getCodigo();
            System.out.println("Cosigo empresa: "+this.codigoEmpresa);
        }
        
        this.busesPorEmpresa = new ArrayList<>();
        for(Bus b : this.buses)
            if (b.getCodigoEmpresa().equals(this.codigoEmpresa)) {
             this.busesPorEmpresa.add(b);        
        }
        cargarAsientosE();
    }
    
    public void cargarAsientosE()
    {
        this.asientos = this.asientoServicio.obtenerTodas();
        
            this.codigoBus = this.busesPorEmpresa.get(0).getCodigo();
            System.out.println("Codigo bus: "+this.codigoBus);
        
        this.asientosPorBus = new ArrayList<>();
        for(Asiento a : this.asientos)
            if (a.getCodigoBus().equals(this.codigoBus)) {
             this.asientosPorBus.add(a);        
        }
        
        this.numeroMaxAsientos=50-this.asientosPorBus.size();
        this.listadoNumeroAsientos = new ArrayList<>();
        for(int i = 1; i<=this.numeroMaxAsientos;i++)
        {
            this.listadoNumeroAsientos.add(i);
        }
    }
    
    public void cargarAsientos()
    {
        this.asientos = this.asientoServicio.obtenerTodas();
        System.out.println("Codigo bus: "+this.codigoBus);
        if(this.codigoBus == null)
        {
            this.codigoBus = this.busesPorEmpresa.get(0).getCodigo();
            System.out.println("Codigo bus: "+this.codigoBus);
        }
        this.asientosPorBus = new ArrayList<>();
        for(Asiento a : this.asientos)
            if (a.getCodigoBus().equals(this.codigoBus)) {
             this.asientosPorBus.add(a);        
        }
        
        this.numeroMaxAsientos=50-this.asientosPorBus.size();
        this.listadoNumeroAsientos = new ArrayList<>();
        for(int i = 1; i<=this.numeroMaxAsientos;i++)
        {
            this.listadoNumeroAsientos.add(i);
        }
    }
    
    @Override
    public void nuevo() {
        super.nuevo();
        this.asiento=new Asiento();
    }
    
    
    @Override
    public void modificar() {

        super.modificar();
        this.asiento=new Asiento();
        
        try {
            BeanUtils.copyProperties(this.asiento,this.asientoSelected);
            //this.empresa =
            
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance(); 
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado",  e.getMessage()));
        }
    }
    
    @Override
    public void eliminar() {
        super.eliminar();
        this.asiento=new Asiento();
        try {
            BeanUtils.copyProperties(this.asiento,this.asientoSelected);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance(); 
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado",  e.getMessage()));
        }
    }
    
    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance(); 
        if (super.isEnNuevo()) {
            try {
                Integer maxNum = 0;
                if(this.asientosPorBus.size()>0)
                    maxNum = Integer.parseInt(this.asientosPorBus.get(this.asientosPorBus.size()-1).getNombre());
                maxNum++;
                for(int i = maxNum;i < (maxNum+this.numeroAsientos);i++)
                {
                    String nombre ="";
                    if(i<10)
                        nombre = "0"+i;
                    else
                        nombre = ""+i;
                    
                    this.asiento = new Asiento();
                    this.asiento.setCodigoBus(this.codigoBus);
                    this.asiento.setNombre(nombre);
                    this.asiento.setCosto(this.costo);
                    this.asiento.setCantidad(i);
                    this.asientoServicio.crearAsiento(this.asiento);
                }
                this.cargarAsientos();
 
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registraron los asientos correctamente", null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnModificar()){
            try {
                
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo el asiento: "+this.asiento.getCodigoAsiento(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnEliminar()){
            try {
               
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó el asiento: "+this.asiento.getCodigoAsiento(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        }
        
        this.cancelar();
    }
    
    public void cancelar() {
        super.reset();
        
    }
    
}
