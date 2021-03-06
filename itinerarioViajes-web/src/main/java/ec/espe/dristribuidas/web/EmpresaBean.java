/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Empresa;
import ec.espe.dristribuidas.servicios.EmpresaServicio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class EmpresaBean extends BaseBean implements Serializable {

    @EJB
    private EmpresaServicio empresaservicio;
    private List<Empresa> empresas;
    private Empresa empresa;
    private Empresa empresaSelected;

    ValidacionesInputBean validacion = new ValidacionesInputBean();

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Empresa getEmpresaSelected() {

        return empresaSelected;
    }

    public void setEmpresaSelected(Empresa empresaSelected) {
        this.empresaSelected = empresaSelected;
    }
    
    

    @PostConstruct
    public void inicializar() {
        empresas = empresaservicio.obtenerTodas();
        //empresaSelected = new Empresa();
    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.empresa = new Empresa();
    }

    @Override
    public void modificar() {

        super.modificar();
        this.empresa = new Empresa();
        try {
            BeanUtils.copyProperties(this.empresa, this.empresaSelected);

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    @Override
    public void eliminar() {
        super.eliminar();
        this.empresa = new Empresa();
        try {
            BeanUtils.copyProperties(this.empresa, this.empresaSelected);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (super.isEnNuevo()) {
            if (validarEmpresa()) {
                try {

                    this.empresaservicio.crearEmpresa(this.empresa);
                    this.empresas.add(0, this.empresa);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la empresa: " + this.empresa.getNombre(), null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede ingresar la empresa ya que contiene datos erroneos ", null));
            }

        } else if (super.isEnModificar()) {
            if (validarEmpresa()) {
                try {
                    this.empresaservicio.actualiarEmpresa(this.empresa);
                    BeanUtils.copyProperties(this.empresaSelected, this.empresa);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo la empresa: " + this.empresa.getNombre(), null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede actualizar la empresa ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnEliminar()) {
            try {
                this.empresaservicio.eliminarEmpresa(this.empresa.getCodigo());
                this.empresas.remove(this.empresa);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino la empresa: " + this.empresa.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }
        this.cancelar();

    }

    public void cancelar() {
        super.reset();
        this.empresa = null;
        this.empresaSelected = null;
    }

    public void validateEmail() {

        String resultado = validacion.validateEmail(empresa.getCorreoElectronico());

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateCodigo() {

        String resultado = validacion.validateNumeroEntero(empresa.getCodigo(), 13);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateNombre() {

        String resultado = validacion.validateTextoSoloLetras(empresa.getNombre(), 100);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateTelefono() {

        String resultado = validacion.validateNumeroEntero(empresa.getTelefono(), 10);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateDireccion() {

        String resultado = validacion.validateTextoLetrasNumerosCaracteresEspeciales(empresa.getDireccion(), 50);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public boolean validarEmpresa() {

        if (validacion.validateEmail(empresa.getCorreoElectronico()) == "se"
                && validacion.validateNumeroEntero(empresa.getCodigo(), 13) == "se"
                && validacion.validateTextoSoloLetras(empresa.getNombre(), 100) == "se"
                && validacion.validateNumeroEntero(empresa.getTelefono(), 10) == "se"
                && validacion.validateTextoLetrasNumerosCaracteresEspeciales(empresa.getDireccion(), 50) == "se") {
            return true;
        } else {
            return false;
        }

    }

}