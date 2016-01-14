/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Asiento;
import ec.espe.dristribuidas.modelo.Boleto;
import ec.espe.dristribuidas.modelo.Frecuencia;
import ec.espe.dristribuidas.modelo.Lugar;
import ec.espe.dristribuidas.servicios.AsientoServicio;
import ec.espe.dristribuidas.servicios.BoletoServicio;
import ec.espe.dristribuidas.servicios.BusServicio;
import ec.espe.dristribuidas.servicios.FrecuenciaServicio;
import ec.espe.dristribuidas.servicios.LugarServicio;
import ec.espe.dristribuidas.servicios.RutaServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class BoletoBean implements Serializable {
    @EJB
    private BoletoServicio boletoServicio;
    private List<Boleto> boletos;
    private List<Boleto> boletosPorFrecuencia;
    private Boleto boleto;
    private Boleto boletoSelected;
    
    @EJB
    private FrecuenciaServicio frecuenciaServicio;
    private List<Frecuencia> frecuencias;
    private List<Frecuencia> frecuenciasPorLugar;
    private Integer codigoFrecuencia;
    
    
    @EJB
    private AsientoServicio asientoServicio;
    private List<Asiento> asientos;
    private List<Asiento> asientosPorBus;
    
    @EJB
    private LugarServicio lugarServicio;
    private List<Lugar> lugares;
    private Integer codigoLugar;

    @EJB
    private RutaServicio rutaServicio;
    
    @EJB
    private BusServicio busServicio;
    
    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public Boleto getBoletoSelected() {
        return boletoSelected;
    }

    public void setBoletoSelected(Boleto boletoSelected) {
        this.boletoSelected = boletoSelected;
    }

    public List<Lugar> getLugares() {
        return lugares;
    }

    public void setLugares(List<Lugar> lugares) {
        this.lugares = lugares;
    }

    public List<Frecuencia> getFrecuencias() {
        return frecuencias;
    }

    public void setFrecuencias(List<Frecuencia> frecuencias) {
        this.frecuencias = frecuencias;
    }

    public List<Frecuencia> getFrecuenciasPorLugar() {
        return frecuenciasPorLugar;
    }

    public void setFrecuenciasPorLugar(List<Frecuencia> frecuenciasPorLugar) {
        this.frecuenciasPorLugar = frecuenciasPorLugar;
    }

    public Integer getCodigoLugar() {
        return codigoLugar;
    }

    public void setCodigoLugar(Integer codigoLugar) {
        this.codigoLugar = codigoLugar;
    }

    public Integer getCodigoFrecuencia() {
        return codigoFrecuencia;
    }

    public void setCodigoFrecuencia(Integer codigoFrecuencia) {
        this.codigoFrecuencia = codigoFrecuencia;
    }

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public void setBoletos(List<Boleto> boletos) {
        this.boletos = boletos;
    }

    public List<Boleto> getBoletosPorFrecuencia() {
        return boletosPorFrecuencia;
    }

    public void setBoletosPorFrecuencia(List<Boleto> boletosPorFrecuencia) {
        this.boletosPorFrecuencia = boletosPorFrecuencia;
    }
    
    
    
    @PostConstruct
    public void inicializar()
    {
        //this.lugares = lugarServicio.obtenerTodas();
        this.cargarLugares();
        this.cargarFrecuencias();
        this.cargarBoletos();
    }
    
    public void cargarLugares()
    {
        this.lugares = lugarServicio.obtenerTodas();
        if(this.codigoLugar==null)
        {
            if(lugares.size()>0)
                this.codigoLugar=this.lugares.get(0).getCodigo();
            else
                this.codigoLugar=null;
        }
    }
    
    public void cargarFrecuencias()
    {
        this.frecuencias=frecuenciaServicio.obtenerTodas();
        this.frecuenciasPorLugar = new ArrayList<>();
        if(this.frecuencias!=null)
        for(int i = 0; i<this.frecuencias.size();i++)
        {
            if(this.frecuencias.get(i).getRuta()==null)
            {
                this.frecuencias.get(i).setRuta(this.rutaServicio.obtenerPorID(this.frecuencias.get(i).getCodigoRuta()));
                this.frecuencias.get(i).setBus(this.busServicio.obtenerPorID(this.frecuencias.get(i).getCodigoBus()));
            }
            if(this.frecuencias.get(i).getRuta().getCodigoLugarSalida().equals(this.codigoLugar))
            {
                this.frecuenciasPorLugar.add(this.frecuencias.get(i));
            }
        }
        if(this.codigoFrecuencia==null)
        {
           if(this.frecuenciasPorLugar.size()>0)
                this.codigoFrecuencia=this.frecuenciasPorLugar.get(0).getCodigo();
           else
                this.codigoFrecuencia=null;
        }
    }
    
    public void cargarFrecuenciasE()
    {
        this.frecuencias=frecuenciaServicio.obtenerTodas();
        this.frecuenciasPorLugar = new ArrayList<>();
        for(int i = 0; i<this.frecuencias.size();i++)
        {
            if(this.frecuencias.get(i).getRuta()==null)
            {
                this.frecuencias.get(i).setRuta(this.rutaServicio.obtenerPorID(this.frecuencias.get(i).getCodigoRuta()));
                this.frecuencias.get(i).setBus(this.busServicio.obtenerPorID(this.frecuencias.get(i).getCodigoBus()));
                
            }
            if(this.frecuencias.get(i).getRuta().getCodigoLugarSalida().equals(this.codigoLugar))
            {
                this.frecuenciasPorLugar.add(this.frecuencias.get(i));
            }
        }
       if(this.frecuenciasPorLugar.size()>0)
        this.codigoFrecuencia=this.frecuenciasPorLugar.get(0).getCodigo();
       else
          this.codigoFrecuencia=null; 
       
        this.cargarBoletos();
    }
    
    public void cargarBoletos()
    {
        this.boletos = this.boletoServicio.obtenerTodas();
        this.boletosPorFrecuencia=new ArrayList<>();
        if(this.boletos!=null)
        for(int i=0;i<this.boletos.size();i++)
        {
            if(this.boletos.get(i).getCodigoFrecuencia().equals(this.codigoFrecuencia))
            {
                this.boletosPorFrecuencia.add(this.boletos.get(i));
            }
        }
        for(int i=0;i<this.boletosPorFrecuencia.size();i++)
        {
            this.boletosPorFrecuencia.get(i).setAsiento(this.asientoServicio.obtenerPorID(this.boletosPorFrecuencia.get(i).getCodigoAsiento()));
            this.boletosPorFrecuencia.get(i).setFrecuencia(this.frecuenciaServicio.obtenerPorID(this.boletosPorFrecuencia.get(i).getCodigoFrecuencia()));
            this.boletosPorFrecuencia.get(i).getFrecuencia().setBus(this.busServicio.obtenerPorID(this.boletosPorFrecuencia.get(i).getFrecuencia().getCodigoBus()));
            this.boletosPorFrecuencia.get(i).getFrecuencia().setRuta(this.rutaServicio.obtenerPorID(this.boletosPorFrecuencia.get(i).getFrecuencia().getCodigoRuta()));
        
        }
    }
    
    public void crearBoletos()
    {
        FacesContext context = FacesContext.getCurrentInstance(); 
        try
        {
            this.asientos = this.asientoServicio.obtenerTodas();
            Frecuencia frecuenciaTmp = this.frecuenciaServicio.obtenerPorID(codigoFrecuencia);
            this.asientosPorBus=new ArrayList<>();
            if(this.asientos!=null)
            for(int i=0; i<this.asientos.size();i++)
            {
                if(this.asientos.get(i).getCodigoBus().equals(frecuenciaTmp.getCodigoBus()))
                {
                    boolean aux = true;
                    for(Boleto b:this.boletosPorFrecuencia)
                    {
                        if(this.asientos.get(i).getCodigoAsiento().equals(b.getCodigoAsiento()))
                        {
                            aux=false;
                            break;
                        }
                    }
                    if(aux)
                        this.asientosPorBus.add(this.asientos.get(i));
                }
            }
            int cont = 0;
            for(Asiento a:asientosPorBus)
            {
                this.boleto=new Boleto();
                this.boleto.setCodigoAsiento(a.getCodigoAsiento());
                this.boleto.setCodigoFrecuencia(this.codigoFrecuencia);
                frecuenciaTmp.setRuta(this.rutaServicio.obtenerPorID(frecuenciaTmp.getCodigoRuta()));
                this.boleto.setCosto(a.getCosto().add(frecuenciaTmp.getRuta().getCosto()));
                this.boleto.setEstado("D");
                this.boletoServicio.crearBoleto(this.boleto);
                cont++;
            }
            this.cargarBoletos();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se crearon correctamente "+cont+" boletos", null));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        } 
    }
    public String obtenerFecha(Date date)
    {
        return date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900)+"-"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
    }
}
