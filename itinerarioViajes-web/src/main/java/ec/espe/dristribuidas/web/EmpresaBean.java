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
import org.primefaces.context.RequestContext;
/**
 *
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class EmpresaBean implements Serializable {
    @EJB
    private EmpresaServicio empresaservicio;
    private List<Empresa> empresas;
    private Empresa empresa =  new Empresa();
    private String codigo;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correoElectronico;

    public List<Empresa> getEmpresas()  {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElctronico) {
        this.correoElectronico = correoElctronico;
    }
    
    
    public void insertar()
    {
        boolean insertado = false;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        try
        {
            empresaservicio.crearEmpresa(this.empresa);
            insertado = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empresa Insertada: ", this.empresa.getNombre());
        }
        catch(RuntimeException e)
        {
            insertado = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al ingresar la empresa", e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
         context.addCallbackParam("estaInsertado", insertado);
    }
    
    public void actualizar()
    {
        boolean insertado = false;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        try
        {
            empresaservicio.actualiarEmpresa(this.empresa);
            insertado = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empresa Actualizada: ", this.empresa.getNombre());
        }
        catch(RuntimeException e)
        {
            insertado = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al actualizar la empresa", e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
         context.addCallbackParam("estaInsertado", insertado);
    }
    
    public void eliminar()
    {
        boolean insertado = false;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        try
        {
            empresaservicio.eliminarEmpresa(this.empresa.getCodigo());
            insertado = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empresa Eliminada: ", this.empresa.getNombre());
        }
        catch(RuntimeException e)
        {
            insertado = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al eliminar la empresa", e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
         context.addCallbackParam("estaInsertado", insertado);
    }
    
    @PostConstruct
   public void inicializar(){
    empresas=empresaservicio.obtenerTodas();
   }

}
