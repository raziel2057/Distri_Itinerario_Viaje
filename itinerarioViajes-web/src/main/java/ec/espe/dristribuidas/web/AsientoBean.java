/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;


import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import static org.hamcrest.Matchers.equalTo;


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
    
    
    @PostConstruct
    public void inicializar()
    {
        this.empresas = this.empresaServicio.obtenerTodas();
        
        this.cargarBuses();
        
        /*this.busesPorEmpresa = Lambda.select(this.buses, having(on(Bus.class).getCodigoEmpresa(),
                Matchers.equalTo("")));*/

        this.asientos=this.asientoServicio.obtenerTodas();
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
    }
    
    public void cargarAsientos()
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
        
    }
    
}
