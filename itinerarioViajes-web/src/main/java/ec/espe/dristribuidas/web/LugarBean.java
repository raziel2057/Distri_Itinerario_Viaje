/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import java.io.Serializable;
import ec.espe.dristribuidas.modelo.Lugar;
import ec.espe.dristribuidas.servicios.LugarServicio;
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
 * @author ale
 */
@ViewScoped
@ManagedBean
public class LugarBean extends BaseBean implements Serializable {

    @EJB
    private LugarServicio lugarServicio;
    private List<Lugar> lugares;
    private Lugar lugar;
    private Lugar lugarSelected;

    ValidacionesInputBean validacion = new ValidacionesInputBean();

    public List<Lugar> getLugares() {
        return lugares;
    }

    public void setLugares(List<Lugar> lugares) {
        this.lugares = lugares;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public Lugar getLugarSelected() {
        return lugarSelected;
    }

    public void setLugarSelected(Lugar lugarSelected) {
        this.lugarSelected = lugarSelected;
    }

    @PostConstruct
    public void inicializar() {
        lugares = lugarServicio.obtenerTodasDesc();

    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.lugar = new Lugar();
    }

    @Override
    public void modificar() {

        super.modificar();
        this.lugar = new Lugar();
        try {
            BeanUtils.copyProperties(this.lugar, this.lugarSelected);

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    @Override
    public void eliminar() {
        super.eliminar();
        this.lugar = new Lugar();
        try {
            BeanUtils.copyProperties(this.lugar, this.lugarSelected);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (super.isEnNuevo()) {
            if (validarLugar()) {
                try {
                    //Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");

                    this.lugarServicio.crearLugar(this.lugar);
                    this.lugares.add(0, this.lugar);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el lugar: " + this.lugar.getNombre(), null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede ingresar el lugar ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnModificar()) {
            if (validarLugar()) {
                try {
                    this.lugarServicio.actualiarLugar(this.lugar);
                    BeanUtils.copyProperties(this.lugarSelected, this.lugar);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo el lugar: " + this.lugar.getNombre(), null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede actualizar el lugar ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnEliminar()) {
            try {
                this.lugarServicio.eliminarLugar(this.lugar.getCodigo());
                this.lugares.remove(this.lugar);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino el lugar: " + this.lugar.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }
        this.cancelar();
    }

    public void cancelar() {
        super.reset();
        this.lugar = null;
        this.lugarSelected = null;
    }

    public void validateNombre() {

        String resultado = validacion.validateTextoSoloLetras(lugar.getNombre(), 50);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public boolean validarLugar() {

        if (validacion.validateTextoSoloLetras(lugar.getNombre(), 50) == "se") {
            return true;
        } else {
            return false;
        }

    }

}
