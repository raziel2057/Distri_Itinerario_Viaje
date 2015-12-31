/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Asiento;
import ec.espe.dristribuidas.modelo.Bus;
import ec.espe.dristribuidas.servicios.AsientoServicio;
import ec.espe.dristribuidas.servicios.BusServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
    private String codigoBus;
    

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
    
    
    @PostConstruct
    public void inicializar()
    {
        this.buses = this.busServicio.obtenerTodas();

        this.asientos=this.asientoServicio.obtenerTodas();
        
    }
    
    public void cargarAsientos()
    {
        this.asiento = new Asiento();
        this.asiento.setCodigoBus(this.codigoBus);
        this.asientos = this.asientoServicio.obtenerTodas();
        
        
    }
    
}
