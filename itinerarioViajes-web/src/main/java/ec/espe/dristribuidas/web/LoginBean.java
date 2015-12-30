/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ec.espe.dristribuidas.servicios.ClienteServicio;
import ec.espe.dristribuidas.modelo.Cliente;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class LoginBean implements Serializable {

    @EJB
    private ClienteServicio clienteServicio = new ClienteServicio();

    private Cliente cliente;
    private String username;
    private String password;
    private boolean loggedIn = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean estaLogeado() {
        return loggedIn;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    

    /*public String login() {
     //RequestContext context = RequestContext.getCurrentInstance();
     FacesContext context = FacesContext.getCurrentInstance(); 
     FacesMessage message = null;
        
     if(username != null  && password != null) {
     this.cliente = clienteServicio.buscarClientePorUsuario(username);
     if(this.cliente!=null && this.cliente.getUsuario().equals(username)&& this.cliente.getClave().equals(password))
     {
     //loggedIn = true;
     //message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
     ((HttpServletRequest)context.getExternalContext().getRequest()).getSession().setAttribute("cliente", this.cliente);
     return "empresaCrud";
     }
     else {
     return "prueba";
     //loggedIn = false;
     /// message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
     }
            
     } 
        
     /*FacesContext.getCurrentInstance().addMessage(null, message);
     context.addCallbackParam("estaLogeado", loggedIn);
     if (loggedIn)
     context.addCallbackParam("view", "faces/empresaCrud.xhtml");
     }*/
    public void login() {
        //FacesContext context = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        //String direccion="null";
        this.cliente = clienteServicio.buscarClientePorUsuario(username);
        if (this.cliente != null && this.cliente.getUsuario().equals(username) && this.cliente.getClave().equals(DigestUtils.md5Hex(password))) {
            //((HttpServletRequest) context.getExternalContext().getRequest()).getSession().setAttribute("usuario", this.cliente);
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Bienvenido"));
            //direccion= "inicio?faces-redirect=true";
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", username);

        } else {
            //FacesContext.getCurrentInstance().addMessage(null, 
            //      new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Usuario y/o password incorrectos"));
            //direccion= "index";
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    "Credenciales no válidas");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("estaLogeado", loggedIn);
        if (loggedIn) {

            if (this.cliente.getTipo().equals("A")) {
                context.addCallbackParam("view", "faces/inicio.xhtml");
            }
            else{
                context.addCallbackParam("view", "faces/inicioRegular.xhtml");
            }
        }

        //return direccion;
    }

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();

        loggedIn = false;
    }

}
