/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Cliente;
import ec.espe.dristribuidas.servicios.ClienteServicio;
import java.io.Serializable;
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
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class ClienteBean extends BaseBean implements Serializable {
    @EJB
    private ClienteServicio clienteServicio;
    private List<Cliente> clientes;
    private Cliente cliente;
    private Cliente clienteSelected;

    public ClienteServicio getClienteServicio() {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }
    
    @PostConstruct
    public void inicializar(){
        clientes = clienteServicio.obtenerTodas();
        
    }
    
    @Override
    public void nuevo() {
        super.nuevo();
        this.cliente = new Cliente();
    }
    
    
    @Override
    public void modificar() {

        super.modificar();
        this.cliente = new Cliente();
        try {
            BeanUtils.copyProperties(this.cliente,this.clienteSelected);
            
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance(); 
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado",  e.getMessage()));
        }
    }
    
    @Override
    public void eliminar() {
        super.eliminar();
        this.cliente = new Cliente();
        try {
            BeanUtils.copyProperties(this.cliente, this.clienteSelected);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance(); 
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado",  e.getMessage()));
        }
    }
    
    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance(); 
        if (super.isEnNuevo()) {
            try {
                //Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");
                this.clienteServicio.crearCliente(this.cliente);
                this.clientes.add(0,this.cliente);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la empresa: "+this.cliente.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnModificar()){
            try {
                this.clienteServicio.actualiarCliente(this.cliente);
                BeanUtils.copyProperties(this.clienteSelected, this.cliente);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo la empresa: "+this.cliente.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        } else if (super.isEnEliminar()){
            try {
                this.clienteServicio.eliminarCliente(this.cliente.getCodigo());
                this.clientes.remove(this.cliente);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino la empresa: "+this.cliente.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } 
        }
        this.cancelar();
    }
    
    
 
    
    
    public void cancelar() {
        super.reset();
        this.cliente = null;
        this.clienteSelected = null;
    }
    
}
