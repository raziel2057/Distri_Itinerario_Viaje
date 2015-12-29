/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Lugar;
import ec.espe.dristribuidas.modelo.Ruta;
import ec.espe.dristribuidas.servicios.LugarServicio;
import ec.espe.dristribuidas.servicios.RutaServicio;
import java.io.Serializable;
import java.util.ArrayList;
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
public class RutaBean extends BaseBean implements Serializable {

    @EJB
    private RutaServicio rutaServicio;
    private List<Ruta> rutas;
    private Ruta ruta;
    private Ruta rutaSelected;
    @EJB
    private LugarServicio lugarServicio;
    private List<Lugar> lugares;
    private List<SelectItem> listaLugares;

    ValidacionesInputBean validacion = new ValidacionesInputBean();

    public RutaServicio getRutaServicio() {
        return rutaServicio;
    }

    public void setRutaServicio(RutaServicio rutaServicio) {
        this.rutaServicio = rutaServicio;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Ruta getRutaSelected() {
        return rutaSelected;
    }

    public void setRutaSelected(Ruta rutaSelected) {
        this.rutaSelected = rutaSelected;
    }

    public List<Lugar> getLugares() {
        return lugares;
    }

    public void setLugares(List<Lugar> lugares) {
        this.lugares = lugares;
    }

    public List<SelectItem> getListaLugares() {
        return listaLugares;
    }

    public void setListaLugares(List<SelectItem> listaLugares) {
        this.listaLugares = listaLugares;
    }

    @PostConstruct
    public void inicializar() {
        rutas = rutaServicio.obtenerTodas();

        lugares = lugarServicio.obtenerTodas();

        listaLugares = new ArrayList<SelectItem>();

        for (Lugar lugare : lugares) {
            listaLugares.add(new SelectItem(lugare.getCodigo(), lugare.getNombre()));
        }

    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.ruta = new Ruta();
    }

    @Override
    public void modificar() {

        super.modificar();
        this.ruta = new Ruta();
        try {
            BeanUtils.copyProperties(this.ruta, this.rutaSelected);

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    @Override
    public void eliminar() {
        super.eliminar();
        this.ruta = new Ruta();
        try {
            BeanUtils.copyProperties(this.ruta, this.rutaSelected);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (super.isEnNuevo()) {
            if (validarRuta()) {
                try {
                    //Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");

                    this.rutaServicio.crearRuta(this.ruta);
                    this.rutas.add(0, this.ruta);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la ruta: " + this.ruta.getNombre(), null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede ingresar la ruta ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnModificar()) {
            if (validarRuta()) {
                try {
                    this.rutaServicio.actualiarRuta(this.ruta);
                    BeanUtils.copyProperties(this.rutaSelected, this.ruta);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo la ruta: " + this.ruta.getNombre(), null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede actualizar la ruta ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnEliminar()) {
            try {
                this.rutaServicio.eliminarRuta(this.ruta.getCodigo());
                this.rutas.remove(this.ruta);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino la ruta: " + this.ruta.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }
        this.cancelar();
    }

    public void cancelar() {
        super.reset();
        this.ruta = null;
        this.rutaSelected = null;
    }

    public void validateNombre() {

        String resultado = validacion.validateTextoSoloLetras(ruta.getNombre(), 50);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateCosto() {

        String resultado = validacion.validateNumeroDecimal(this.ruta.getCosto().toString(), 6);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateTiempoHoras() {

        String resultado = validacion.validateNumeroDecimal(this.ruta.getTiempoHoras().toString(), 5);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateKilometros() {

        String resultado = validacion.validateNumeroDecimal(this.ruta.getKilometros().toString(), 10);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateCodigoSalidaLlegada() {

        if (this.ruta.getCodigoLugarSalida() == this.ruta.getCodigoLugarDestino()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El lugar de salida no puede ser el mismo que el de llegada"));
        }
    }

    public boolean validarRuta() {

        validateCodigoSalidaLlegada();

        if (validacion.validateTextoSoloLetras(ruta.getNombre(), 50) == "se"
                && validacion.validateNumeroDecimal(this.ruta.getCosto().toString(), 6) == "se"
                && validacion.validateNumeroDecimal(this.ruta.getTiempoHoras().toString(), 5) == "se"
                && validacion.validateNumeroDecimal(this.ruta.getKilometros().toString(), 10) == "se"
                && this.ruta.getCodigoLugarSalida() != this.ruta.getCodigoLugarDestino()) {
            return true;
        } else {
            return false;
        }
    }

}
