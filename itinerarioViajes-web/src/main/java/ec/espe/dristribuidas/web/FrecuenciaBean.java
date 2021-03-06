/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Bus;
import ec.espe.dristribuidas.modelo.Empresa;
import ec.espe.dristribuidas.modelo.Frecuencia;
import ec.espe.dristribuidas.modelo.Ruta;
import ec.espe.dristribuidas.servicios.BusServicio;
import ec.espe.dristribuidas.servicios.EmpresaServicio;
import ec.espe.dristribuidas.servicios.FrecuenciaServicio;
import ec.espe.dristribuidas.servicios.RutaServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author ale
 */
@ViewScoped
@ManagedBean
public class FrecuenciaBean extends BaseBean implements Serializable {

    @EJB
    private FrecuenciaServicio frecuenciaServicio;
    private List<Frecuencia> frecuencias;
    private Frecuencia frecuencia;
    private Frecuencia frecuenciaSelected;

    @EJB
    private RutaServicio rutaServicio;
    private List<Ruta> rutas;
    private List<SelectItem> listaRutas;

    @EJB
    private BusServicio busServicio;
    private List<Bus> buses;
    private List<SelectItem> listaBuses;

    @EJB
    private EmpresaServicio empresaServicio;

    ValidacionesInputBean validacion = new ValidacionesInputBean();

    private Date fechaActual;
    private Date fechaMinimaFrecuencia;
    private Date fechaMaximaFrecuencia;

    public List<Frecuencia> getFrecuencias() {
        return frecuencias;
    }

    public void setFrecuencias(List<Frecuencia> frecuencias) {
        this.frecuencias = frecuencias;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Frecuencia getFrecuenciaSelected() {
        return frecuenciaSelected;
    }

    public void setFrecuenciaSelected(Frecuencia frecuenciaSelected) {
        this.frecuenciaSelected = frecuenciaSelected;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    public List<SelectItem> getListaRutas() {
        return listaRutas;
    }

    public void setListaRutas(List<SelectItem> listaRutas) {
        this.listaRutas = listaRutas;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    public List<SelectItem> getListaBuses() {
        return listaBuses;
    }

    public void setListaBuses(List<SelectItem> listaBuses) {
        this.listaBuses = listaBuses;
    }

    public Date getFechaMinimaFrecuencia() {
        return fechaMinimaFrecuencia;
    }

    public Date getFechaMaximaFrecuencia() {
        return fechaMaximaFrecuencia;
    }

    @PostConstruct
    public void inicializar() {

        frecuencias = frecuenciaServicio.obtenerTodas();

        //Cargar rutas
        rutas = rutaServicio.obtenerTodas();
        listaRutas = new ArrayList<SelectItem>();

        for (Ruta rut : rutas) {
            listaRutas.add(new SelectItem(rut.getCodigo(), rut.getNombre()));
        }

        Ruta rutaTemp;
        for (int i = 0; i < frecuencias.size(); i++) {
            rutaTemp = rutaServicio.obtenerPorID(frecuencias.get(i).getCodigoRuta());

            try {
                //BeanUtils.copyProperties(this.buses.get(i).getEmpresa(), empresaTmp);
                this.frecuencias.get(i).setRuta(rutaTemp);
            } catch (Exception e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
            }
            rutaTemp = null;
        }
        //Cargar buses
        buses = busServicio.obtenerTodas();
        listaBuses = new ArrayList<SelectItem>();

        Empresa empresaTemp;
        for (Bus bu : buses) {

            if (bu.getEstado().equals("A")) {
                empresaTemp = empresaServicio.obtenerPorID(bu.getCodigoEmpresa());
                listaBuses.add(new SelectItem(bu.getCodigo(), bu.getCodigo() + " - " + empresaTemp.getNombre()));
                empresaTemp = null;
            }
        }

        fechaActual = new Date();
        fechaMinimaFrecuencia = new Date();
        fechaMinimaFrecuencia.setHours(fechaActual.getHours() + 2);
        fechaMaximaFrecuencia = new Date();
        fechaMaximaFrecuencia.setMonth(fechaMinimaFrecuencia.getMonth() + 3);
    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.frecuencia = new Frecuencia();
    }

    @Override
    public void modificar() {

        super.modificar();
        this.frecuencia = new Frecuencia();
        try {
            BeanUtils.copyProperties(this.frecuencia, this.frecuenciaSelected);

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    @Override
    public void eliminar() {
        super.eliminar();
        this.frecuencia = new Frecuencia();
        try {
            BeanUtils.copyProperties(this.frecuencia, this.frecuenciaSelected);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public Date calcularFechaLlegada() {

        Date fecha = new Date();
        double tiempoHoras = 0;
        double aux;
        int dias;
        int horas;
        int minutos;

        dias = 0;
        horas = 0;
        minutos = 0;
        tiempoHoras = rutaServicio.obtenerPorID(this.frecuencia.getCodigoRuta()).getTiempoHoras().doubleValue();
        horas = (int) tiempoHoras;
        aux = tiempoHoras - horas;
        if (aux >= 0 && aux <= 0.25) {
            minutos = 15;
        } else if (aux > 0.25 && aux <= 0.5) {
            minutos = 30;
        } else if (aux > 0.5 && aux <= 0.75) {
            minutos = 45;
        } else {
            minutos = 0;
            horas = horas + 1;
        }

        if (horas >= 24) {
            dias = horas / 24;
            horas = horas % 24;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(this.frecuencia.getFechaSalida());
        c.add(Calendar.DATE, dias);
        c.add(Calendar.HOUR_OF_DAY, horas);
        c.add(Calendar.MINUTE, minutos);
        fecha = c.getTime();

        return fecha;
    }

    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (this.frecuencia.getFechaSalida() != null && this.frecuencia.getCodigoRuta() != null) {
            this.frecuencia.setFechaLlegada(calcularFechaLlegada());
        }

        if (super.isEnNuevo()) {
            if (validarFrecuencia()) {
                try {
                    this.frecuenciaServicio.crearFrencuencia(this.frecuencia);
                    this.frecuencias.add(0, this.frecuencia);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la frecuencia", null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede ingresar la ruta ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnModificar()) {
            if (validarFrecuencia()) {
                try {
                    this.frecuenciaServicio.actualiarFrecuencia(this.frecuencia);
                    BeanUtils.copyProperties(this.frecuenciaSelected, this.frecuencia);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo la frecuencia ", null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede actualizar la frecuencia ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnEliminar()) {
            try {
                this.frecuenciaServicio.eliminarFrecuencia(this.frecuencia.getCodigo());
                this.rutas.remove(this.frecuencia);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino la frecuencia ", null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }
        this.cancelar();
        inicializar();
    }

    public void cancelar() {
        super.reset();
        this.frecuencia = null;
        this.frecuenciaSelected = null;
    }

    public void validateFechaSalida() {

        if (this.frecuencia.getFechaSalida() != null && this.frecuencia.getFechaLlegada() != null) {
            if (this.frecuencia.getFechaLlegada().after(this.frecuencia.getFechaSalida())) {

            } else if (this.frecuencia.getFechaLlegada().equals(this.frecuencia.getFechaSalida())) {

            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de salida no puede ser mayor que la de llegada"));
            }
        }

    }

    public void validateFechaLlegada() {

        if (this.frecuencia.getFechaSalida() != null && this.frecuencia.getFechaLlegada() != null) {
            if (this.frecuencia.getFechaLlegada().after(this.frecuencia.getFechaSalida())) {

            } else if (this.frecuencia.getFechaLlegada().equals(this.frecuencia.getFechaSalida())) {

            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de llegada no puede ser menor que la de salida"));
            }
        }

    }

    private boolean validarFrecuencia() {

        if (this.frecuencia.getCodigoBus() != null
                && this.frecuencia.getCodigoRuta() != null
                && this.frecuencia.getFechaSalida() != null) {
            return true;
        } else {
            return false;
        }

    }

}
