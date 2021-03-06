/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Cliente;
import ec.espe.dristribuidas.servicios.ClienteServicio;
import ec.espe.dristribuidas.utils.Correo;
import ec.espe.dristribuidas.utils.RandomString;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;

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

    ValidacionesInputBean validacion = new ValidacionesInputBean();

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean datosLogin;

    private Cliente clienteSesion;
    private String viejaClave;
    private String nuevaClave; //para cuando se actualizan los datos del cliente
    //Para el nuevocliente
    private boolean enRegistro;

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

    public boolean isEnRegistro() {
        return enRegistro;
    }

    public void setEnRegistro(boolean enRegistro) {
        this.enRegistro = enRegistro;
    }

    public LoginBean getDatosLogin() {
        return datosLogin;
    }

    public void setDatosLogin(LoginBean datosLogin) {
        this.datosLogin = datosLogin;
    }

    public Cliente getClienteSesion() {
        return clienteSesion;
    }

    public void setClienteSesion(Cliente clienteSesion) {
        this.clienteSesion = clienteSesion;
    }

    public String getNuevaClave() {
        return nuevaClave;
    }

    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
    }

    public String getViejaClave() {
        return viejaClave;
    }

    public void setViejaClave(String viejaClave) {
        this.viejaClave = viejaClave;
    }

    @PostConstruct
    public void inicializar() {
        clientes = clienteServicio.obtenerTodas();
        enRegistro = false;

        if (this.getDatosLogin().isEnNuuevoCliente()) {

        } else {
            //Para tener los datos del cliente de la sesion.
            clienteSesion = new Cliente();
            try {
                BeanUtils.copyProperties(this.clienteSesion, this.getDatosLogin().getCliente());

            } catch (Exception e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
            }
        }
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
            BeanUtils.copyProperties(this.cliente, this.clienteSelected);

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
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
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (super.isEnNuevo()) {
            if (validacion.validateTextoSoloLetras(cliente.getNombre(), 100) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Nombre no valido ", null));
            } else if (validacion.validateNumeroEntero(cliente.getIdentificacion(), 13) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Identificacion no valida ", null));
            } else if (validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getDireccion(), 100) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Direccion no valida ", null));
            } else if (validacion.validateNumeroEntero(cliente.getTelefono(), 10) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Telefono no valido ", null));
            } else if (validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getUsuario(), 20) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Usuario no valido ", null));
            } else if (validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getClave(), 32) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Calve no valida ", null));
            } else if (validacion.validateEmail(cliente.getCorreoElectronico()) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Correo electronico no valido ", null));
            } else if (clienteServicio.buscarClientePorUsuario(cliente.getUsuario()) != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Nombre de usuario ya utilizado ", null));
            } else {
                try {
                    String claveEncriptada = DigestUtils.md5Hex(this.cliente.getClave());
                    this.cliente.setClave(claveEncriptada);
                    this.clienteServicio.crearCliente(this.cliente);
                    this.clientes.add(0, this.cliente);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el cliente: " + this.cliente.getNombre(), null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            }
        } else if (super.isEnModificar()) {

            if (validacion.validateTextoSoloLetras(cliente.getNombre(), 100) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Nombre no valido ", null));
            } else if (validacion.validateNumeroEntero(cliente.getIdentificacion(), 13) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Identificacion no valida ", null));
            } else if (validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getDireccion(), 100) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Direccion no valida ", null));
            } else if (validacion.validateNumeroEntero(cliente.getTelefono(), 10) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Telefono no valido ", null));
            } else if (validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getUsuario(), 20) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Usuario no valido ", null));
            } else if (validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getClave(), 32) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Calve no valida ", null));
            } else if (validacion.validateEmail(cliente.getCorreoElectronico()) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Correo electronico no valido ", null));
            } else if (clienteServicio.buscarClientePorUsuario(cliente.getUsuario()) != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Nombre de usuario ya utilizado ", null));
            } else {
                try {

                    String claveEncriptada = DigestUtils.md5Hex(this.cliente.getClave());
                    this.cliente.setClave(claveEncriptada);
                    this.clienteServicio.actualiarCliente(this.cliente);
                    BeanUtils.copyProperties(this.clienteSelected, this.cliente);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo el cliente: " + this.cliente.getNombre(), null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            }

        } else if (super.isEnEliminar()) {
            try {
                this.clienteServicio.eliminarCliente(this.cliente.getCodigo());
                this.clientes.remove(this.cliente);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino el cliente: " + this.cliente.getNombre(), null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }
        this.cancelar();
    }

    public void resgistroCliente() {
        FacesContext context = FacesContext.getCurrentInstance();

        Correo correo = new Correo();

        if (super.isEnNuevo()) {

            if (validacion.validateTextoSoloLetras(cliente.getNombre(), 100) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Nombre no valido ", null));
            } else if (validacion.validateNumeroEntero(cliente.getIdentificacion(), 13) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Identificacion no valida ", null));
            } else if (validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getDireccion(), 100) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Direccion no valida ", null));
            } else if (validacion.validateNumeroEntero(cliente.getTelefono(), 10) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Telefono no valido ", null));
            } else if (validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getUsuario(), 20) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Usuario no valido ", null));
            } else if (validacion.validateEmail(cliente.getCorreoElectronico()) != "se") {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Correo electronico no valido ", null));
            } else if (clienteServicio.buscarClientePorUsuario(cliente.getUsuario()) != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Nombre de usuario ya utilizado ", null));
            } else {
                try {
                    RandomString randomString = new RandomString(5); //Clave de 5 caracteres
                    String claveTemporal = randomString.nextString();
                    String claveEncriptada = DigestUtils.md5Hex(claveTemporal);
                    this.cliente.setClave(claveEncriptada);
                    this.cliente.setTipo("R");
                    this.clienteServicio.crearCliente(this.cliente);

                    //Enviar el correo de comprobacion
                    String subject = "Activicación de cuenta SAIV";

                    String cuerpo = "<h2>Activicación de cuenta SAIV</h2>"
                            + "<h4>Felicidades usuario " + this.cliente.getUsuario() + ", su cuenta ha sido activada. Se le ha asignado la siguiente clave automaticamente: " + claveTemporal + " </h4>"
                            + "<h4>Por favor ingrese con el usuario que registró  y esta clave. Posteriormente diríjase a la pestaña de "
                            + "Información personal y cambie su clave</h4>";

                    correo.EnviarCorreoSinArchivoAdjunto(this.cliente.getCorreoElectronico(), subject, cuerpo);

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el cliente: " + this.cliente.getNombre(), null));
                    enRegistro = true;
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            }

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No esta en nuevo", null));
        }

    }

    public void cancelar() {
        super.reset();
        this.cliente = null;
        this.clienteSelected = null;

    }

    public void confirmarClaveModificarDatosClienteSesion() {

        String claveEncriptada = DigestUtils.md5Hex(this.viejaClave.trim());
        if (claveEncriptada.equals(this.clienteSesion.getClave())) {
            nuevaClave = viejaClave;
            modificarDatosClienteSesion();

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Las claves no coinciden "));
            this.cerrar();
        }
    }

    public void cerrar() {
        this.viejaClave = "";
        super.reset();
        this.cliente = null;
    }

    public void modificarDatosClienteSesion() {

        super.modificar();

        this.cliente = new Cliente();
        try {
            BeanUtils.copyProperties(this.cliente, this.clienteSesion);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }

    }

    public void aceptarDatosClienteSesion() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (super.isEnModificar()) {
            if (validarCliente()) {
                try {
                    String claveEncriptada = DigestUtils.md5Hex(nuevaClave);
                    this.cliente.setClave(claveEncriptada);
                    this.clienteServicio.actualiarCliente(this.cliente);
                    BeanUtils.copyProperties(this.clienteSesion, this.cliente);
                    viejaClave = nuevaClave;
                    nuevaClave = "";
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Su informacion ha sido actualizada", null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede actualizar su información ya que contiene datos erroneos ", null));
            }
        }
        this.cerrar();
    }

    public void validateNombre() {

        String resultado = validacion.validateTextoSoloLetras(cliente.getNombre(), 100);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateIdentificacion() {

        String resultado = validacion.validateNumeroEntero(cliente.getIdentificacion(), 13);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateDireccion() {

        String resultado = validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getDireccion(), 100);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateTelefono() {

        String resultado = validacion.validateNumeroEntero(cliente.getTelefono(), 10);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateUsuario() {

        String resultado = validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getUsuario(), 20);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        } else if (clienteServicio.buscarClientePorUsuario(cliente.getUsuario()) != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nombre de usuario ya esta registrado"));
        }
    }

    public void validateClave() {

        String resultado = validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getClave(), 32);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public void validateEmail() {

        String resultado = validacion.validateEmail(cliente.getCorreoElectronico());

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public boolean validarCliente() {

        if (validacion.validateTextoSoloLetras(cliente.getNombre(), 100) == "se"
                && validacion.validateNumeroEntero(cliente.getIdentificacion(), 13) == "se"
                && validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getDireccion(), 100) == "se"
                && validacion.validateNumeroEntero(cliente.getTelefono(), 10) == "se"
                && validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getUsuario(), 20) == "se"
                && validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getClave(), 32) == "se"
                && validacion.validateEmail(cliente.getCorreoElectronico()) == "se"
                && clienteServicio.buscarClientePorUsuario(cliente.getUsuario()) != null) {
            return true;
        } else {
            return false;
        }

    }

    public boolean validarClienteSinClave() {

        if (validacion.validateTextoSoloLetras(cliente.getNombre(), 100) == "se"
                && validacion.validateNumeroEntero(cliente.getIdentificacion(), 13) == "se"
                && validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getDireccion(), 100) == "se"
                && validacion.validateNumeroEntero(cliente.getTelefono(), 10) == "se"
                && validacion.validateTextoLetrasNumerosCaracteresEspeciales(cliente.getUsuario(), 20) == "se"
                && validacion.validateEmail(cliente.getCorreoElectronico()) == "se"
                && clienteServicio.buscarClientePorUsuario(cliente.getUsuario()) != null) {
            return true;
        } else {
            return false;
        }

    }
}
