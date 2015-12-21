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
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class InicioBean implements Serializable {

    @EJB
    private Cliente cliente;
    private String username;
    private String password;

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


    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        
        ((HttpServletRequest) context.getExternalContext().getRequest()).getSession().setAttribute("usuario", this.cliente);
        return "empresaCrud?faces-redirect=true";
       
    }


}
