/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Itinerario;
import ec.espe.dristribuidas.modelo.Lugar;
import ec.espe.dristribuidas.servicios.DetalleItinerarioServicio;
import ec.espe.dristribuidas.servicios.ItinerarioServicio;
import ec.espe.dristribuidas.servicios.LugarServicio;
import java.io.Serializable;
import java.util.Date;
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
public class ItinerarioBean implements Serializable {
    @EJB
    private ItinerarioServicio itinerarioServicio;
    private List<Itinerario> itinearios;
    private List<Itinerario> itineariosPorCliente;
    private Itinerario itinerario;
    private Date fechaSalida;
    private Date fechaActual;
    private Date fechaMaxima;
    @EJB
    private DetalleItinerarioServicio detalleItinerarioServicio;
    
    
    @EJB
    private LugarServicio lugarServicio;
    private List<Lugar> lugaresSalida;
    private List<Lugar> lugaresLlegada;
    private Integer codigoLugarSalida;
    private Integer codigoLugarLlegada;

    public List<Itinerario> getItinearios() {
        return itinearios;
    }

    public void setItinearios(List<Itinerario> itinearios) {
        this.itinearios = itinearios;
    }

    public List<Itinerario> getItineariosPorCliente() {
        return itineariosPorCliente;
    }

    public void setItineariosPorCliente(List<Itinerario> itineariosPorCliente) {
        this.itineariosPorCliente = itineariosPorCliente;
    }

    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Date getFechaMaxima() {
        return fechaMaxima;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }
    
    

    public List<Lugar> getLugaresSalida() {
        return lugaresSalida;
    }

    public void setLugaresSalida(List<Lugar> lugaresSalida) {
        this.lugaresSalida = lugaresSalida;
    }

    public List<Lugar> getLugaresLlegada() {
        return lugaresLlegada;
    }

    public void setLugaresLlegada(List<Lugar> lugaresLlegada) {
        this.lugaresLlegada = lugaresLlegada;
    }

    public Integer getCodigoLugarSalida() {
        return codigoLugarSalida;
    }

    public void setCodigoLugarSalida(Integer codigoLugarSalida) {
        this.codigoLugarSalida = codigoLugarSalida;
    }

    public Integer getCodigoLugarLlegada() {
        return codigoLugarLlegada;
    }

    public void setCodigoLugarLlegada(Integer codigoLugarLlegada) {
        this.codigoLugarLlegada = codigoLugarLlegada;
    }
    
    
    
    
    @PostConstruct
    public void inicializar()
    {
        this.definirFechas();
        this.cargarLugaresSalida();
        this.cargarLugaresLlegada();
    }
    
    public void definirFechas()
    {
        this.fechaActual = new Date();
        this.fechaActual.setHours(this.fechaActual.getHours()+2);
        this.fechaMaxima = new Date();
        this.fechaMaxima.setHours(this.fechaMaxima.getHours()+2);
        this.fechaMaxima.setMonth(this.fechaMaxima.getMonth()+3);
    }
    
    public void cargarLugaresSalida()
    {
        this.lugaresSalida = this.lugarServicio.obtenerTodas();
        if(this.lugaresSalida.size()>0)
            this.codigoLugarSalida = this.lugaresSalida.get(0).getCodigo();
    }
    
    public void cargarLugaresLlegada()
    {
        this.lugaresLlegada = this.lugarServicio.obtenerTodas();
        for(int i=0;i<this.lugaresLlegada.size();i++)
        {
            if(this.lugaresLlegada.get(i).getCodigo().equals(this.codigoLugarSalida))
            {
                this.lugaresLlegada.remove(i);
            }
        }
        if(this.lugaresLlegada.size()>0)
            this.codigoLugarLlegada=this.lugaresLlegada.get(0).getCodigo();
    }
}
