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
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class LoginBean {
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

    
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        
        if(username != null  && password != null) {
            this.cliente = clienteServicio.buscarClientePorUsuario(username);
            if(this.cliente!=null && this.cliente.getUsuario().equals(username)&& this.cliente.getClave().equals(password))
            {
                loggedIn = true;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
            }
            else {
                loggedIn = false;
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
            }
            
        } 
        
         FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("estaLogeado", loggedIn);
            if (loggedIn)
              context.addCallbackParam("view", "faces/empresaCrud.xhtml");
    }
    
    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance() 
                                            .getExternalContext().getSession(false);
        session.invalidate();
        loggedIn = false;
      }
    
}
