/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Frecuencia;
import ec.espe.dristribuidas.modelo.Itinerario;
import ec.espe.dristribuidas.modelo.Lugar;
import ec.espe.dristribuidas.servicios.BusServicio;
import ec.espe.dristribuidas.servicios.DetalleItinerarioServicio;
import ec.espe.dristribuidas.servicios.FrecuenciaServicio;
import ec.espe.dristribuidas.servicios.ItinerarioServicio;
import ec.espe.dristribuidas.servicios.LugarServicio;
import ec.espe.dristribuidas.servicios.RutaServicio;
import ec.espe.dristribuidas.utils.ItinerarioUtil;
import java.io.Serializable;
import java.util.ArrayList;
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
    private List<ItinerarioUtil> posiblesItinerarios;
    @EJB
    private DetalleItinerarioServicio detalleItinerarioServicio;
    
    
    @EJB
    private LugarServicio lugarServicio;
    private List<Lugar> lugaresSalida;
    private List<Lugar> lugaresLlegada;
    private Integer codigoLugarSalida;
    private Integer codigoLugarLlegada;
    
    @EJB
    private FrecuenciaServicio frecuenciaServicio;
    private List<Frecuencia> posibleItinerario;
    private List<Frecuencia> frecuencias;
    
    @EJB
    private BusServicio busServicio;
    
    @EJB
    private RutaServicio rutaServicio;

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

    public List<ItinerarioUtil> getPosiblesItinerarios() {
        return posiblesItinerarios;
    }

    public void setPosiblesItinerarios(List<ItinerarioUtil> posiblesItinerarios) {
        this.posiblesItinerarios = posiblesItinerarios;
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

    public List<Frecuencia> getPosibleItinerario() {
        return posibleItinerario;
    }

    public void setPosibleItinerario(List<Frecuencia> posibleItinerario) {
        this.posibleItinerario = posibleItinerario;
    }

    public List<Frecuencia> getFrecuencias() {
        return frecuencias;
    }

    public void setFrecuencias(List<Frecuencia> frecuencias) {
        this.frecuencias = frecuencias;
    }
    
    
    
    
    @PostConstruct
    public void inicializar()
    {
        this.definirFechas();
        this.cargarLugaresSalida();
        this.cargarLugaresLlegada();
        this.posiblesItinerarios = new ArrayList<>();
    }
    
    public void definirFechas()
    {
        this.fechaActual = new Date();
        this.fechaActual.setHours(this.fechaActual.getHours()+2);
        this.fechaMaxima = new Date();
        this.fechaMaxima.setHours(this.fechaMaxima.getHours()+2);
        this.fechaMaxima.setMonth(this.fechaMaxima.getMonth()+3);
        this.fechaSalida = new Date();
        this.fechaSalida.setHours(this.fechaSalida.getHours()+2);
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
    
    public void cargarFrecuencias()
    {
        this.frecuencias=this.frecuenciaServicio.obtenerTodas();
        for(int i=0;i<this.frecuencias.size();i++)
        {
            this.frecuencias.get(i).setBus(this.busServicio.obtenerPorID(this.frecuencias.get(i).getCodigoBus()));
            this.frecuencias.get(i).setRuta(this.rutaServicio.obtenerPorID(this.frecuencias.get(i).getCodigoRuta()));
        }
    }
    
    public void buscarItinerarios()
    {
        this.cargarFrecuencias();
        this.bi(this.codigoLugarSalida, this.posibleItinerario);
    }
    
    public void bi(Integer codigoLugarSalida,List<Frecuencia> posibleItinerario)
    {
        for(int i=0;i<this.frecuencias.size();i++)
        {
            
            if(this.frecuencias.get(i).getRuta().getCodigoLugarSalida().equals(codigoLugarSalida))
            {

                if(codigoLugarSalida.equals(this.codigoLugarSalida))
                {
                    posibleItinerario =  new ArrayList<>();
                }
                
                if(posibleItinerario.size()>0)
                {
                    int flag=0;
                    for(int j=0;j<posibleItinerario.size();j++)
                    {
                        if(flag==1)
                        {
                            posibleItinerario.remove(j);
                            j--;
                        }
                        if(posibleItinerario.get(j).getRuta().getCodigoLugarDestino().equals(codigoLugarSalida))
                        {
                            flag=1;
                        }
                    }
                }
                
                int flagBP = 0;
                
                if(posibleItinerario.size()>0)
                {
                    for(int j=0;j<posibleItinerario.size();j++)
                    {
                        
                        if((posibleItinerario.get(j).getRuta().getCodigoLugarSalida().equals(this.frecuencias.get(i).getRuta().getCodigoLugarDestino())) || (posibleItinerario.get(j).getRuta().getCodigoLugarDestino().equals(this.frecuencias.get(i).getRuta().getCodigoLugarDestino())))                   
                        {
                            flagBP=1;
                        }
                        
                    }
                }
                
                if(flagBP==0)
                {
                    posibleItinerario.add(this.frecuencias.get(i));
                    if(this.frecuencias.get(i).getRuta().getCodigoLugarDestino().equals(this.codigoLugarLlegada))
                    {
                        ItinerarioUtil tmp = new ItinerarioUtil();
                        tmp.setFrecuencias(posibleItinerario);
                        this.posiblesItinerarios.add(tmp);
                    }
                    else
                    {
                        this.bi(this.frecuencias.get(i).getRuta().getCodigoLugarDestino(), posibleItinerario);
                    }
                }
            }
        }
    }
}
