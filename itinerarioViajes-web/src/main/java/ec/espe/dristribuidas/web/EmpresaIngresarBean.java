/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

/**
 *
 * @author ale
 */
import java.util.Hashtable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

@ManagedBean
@SessionScoped
public class EmpresaIngresarBean implements java.io.Serializable {

    ValidacionesInputBean validacion = new ValidacionesInputBean();

    public EmpresaIngresarBean() {
    }
    String emailAddress;

    public void setEmailAddress(String s) {
        emailAddress = s;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void validateEmail() {

        String resultado = validacion.validateEmail(getEmailAddress());

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void process(ActionEvent event) {
        // Validate again 
        String email = getEmailAddress();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                        getEmailAddress() + " noorreo valido"));
        return;

    }

}
