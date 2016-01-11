/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Cliente;
import ec.espe.dristribuidas.modelo.DetalleFactura;
import ec.espe.dristribuidas.modelo.Factura;
import ec.espe.dristribuidas.reportes.ModeloDetalleFactura;
import ec.espe.dristribuidas.reportes.ModeloFactura;
import ec.espe.dristribuidas.servicios.DetalleFacturaServicio;
import ec.espe.dristribuidas.servicios.FacturaServicio;
import ec.espe.dristribuidas.utils.Correo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author ale
 */
@ViewScoped
@ManagedBean
public class FacturaBean extends BaseBean implements Serializable {

    @EJB
    private FacturaServicio facturaServicio;
    private List<Factura> facturas; //Todas las facturas de la table Facturas
    private List<Factura> facturasCliente; //Todas las facturas asociadas a un cliente
    private Factura factura;
    private Factura facturaSelected;

    @EJB
    private DetalleFacturaServicio detalleFacturaServicio;
    private List<DetalleFactura> detallesFactura; //Todos los detalles de una factura

    ValidacionesInputBean validacion = new ValidacionesInputBean();

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean datosLogin;

    private Cliente cliente;

    public LoginBean getDatosLogin() {
        return datosLogin;
    }

    public void setDatosLogin(LoginBean datosLogin) {
        this.datosLogin = datosLogin;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Factura getFacturaSelected() {
        return facturaSelected;
    }

    public void setFacturaSelected(Factura facturaSelected) {
        this.facturaSelected = facturaSelected;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Factura> getFacturasCliente() {
        return facturasCliente;
    }

    public void setFacturasCliente(List<Factura> facturasCliente) {
        this.facturasCliente = facturasCliente;
    }

    public List<DetalleFactura> getDetallesFactura() {
        return detallesFactura;
    }

    public void setDetallesFactura(List<DetalleFactura> detallesFactura) {
        this.detallesFactura = detallesFactura;
    }

    @PostConstruct
    public void inicializar() {

        cliente = new Cliente();
        try {
            BeanUtils.copyProperties(this.cliente, this.getDatosLogin().getCliente());

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }

        facturas = facturaServicio.obtenerTodas();
        facturasCliente = facturaServicio.obtenerTodasPorIDCliente(this.cliente.getCodigo());

    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.factura = new Factura();
        //Para la nueva factura aqui se deber[ia cargar el factura.cliente con this.cliente
    }

    @Override
    public void modificar() {

        super.modificar();
        this.factura = new Factura();
        try {
            BeanUtils.copyProperties(this.factura, this.facturaSelected);

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    @Override
    public void eliminar() {
        super.eliminar();
        this.factura = new Factura();
        try {
            BeanUtils.copyProperties(this.factura, this.facturaSelected);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (super.isEnNuevo()) {
            if (validarFactura()) {
                try {
                    //Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");

                    this.facturaServicio.crearFactura(this.factura);
                    this.facturas.add(0, this.factura);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la factura ", null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede ingresar la factura ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnModificar()) {
            if (validarFactura()) {
                try {
                    this.facturaServicio.actualiarFactura(this.factura);
                    BeanUtils.copyProperties(this.facturaSelected, this.factura);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se actualizo la factura ", null));
                } catch (Exception e) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede actualizar la factura ya que contiene datos erroneos ", null));
            }
        } else if (super.isEnEliminar()) {
            try {
                this.facturaServicio.eliminarFactura(this.factura.getCodigo());
                this.facturas.remove(this.factura);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimino la factura ", null));
            } catch (Exception e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }
        this.cancelar();
    }

    public void cancelar() {
        super.reset();
        this.factura = null;
        this.facturaSelected = null;
    }

    public void mostrarDetallesFactura() {
        detallesFactura = detalleFacturaServicio.obtenerTodasPorIDFactura(this.facturaSelected.getCodigo());

    }

    public void validateCostoTotal() {

        String resultado = validacion.validateNumeroDecimal(this.factura.getCostoTotal().toString(), 7);

        if (!resultado.equals("se")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", resultado));
        }
    }

    public boolean validarFactura() {

        if (validacion.validateNumeroDecimal(this.factura.getCostoTotal().toString(), 7) == "se") {
            return true;
        } else {
            return false;
        }

    }

    public void facturaPDF() throws JRException, IOException {

        List<Cliente> clientes;
        List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
        String nombreReporte = cliente.getCodigo().toString() + this.facturaSelected.getCodigo().toString() + ".pdf";
        String urlDestinoReporte = "D:\\reporte" + nombreReporte;

        clientes = new ArrayList<Cliente>();
        clientes.add(this.cliente);
        //detallesFactura=detalleFacturaServicio.obtenerTodasPorIDFactura(1); esto ya debe estar lleno con facturaSelected

        JasperPrint jasperPrint;
        JasperPrint jasperPrint2;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            JRBeanCollectionDataSource beanCollectionDataSource2 = new JRBeanCollectionDataSource(clientes);
            String reportPath2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteCliente.jasper");
            jasperPrint2 = JasperFillManager.fillReport(reportPath2, new HashMap(), beanCollectionDataSource2);

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detallesFactura);
            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteDetalleFactura.jasper");
            jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);

            jasperPrint2.setPageHeight(150);
            jprintlist.add(jasperPrint2);
            jprintlist.add(jasperPrint);

            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);

            OutputStream output = new FileOutputStream(new File(urlDestinoReporte));

            exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);
            exporter.exportReport();

            Correo correo = new Correo();

            correo.EnviarCorreoConArchivoAdjunto(cliente.getCorreoElectronico(),
                    "Factura SAIV", "Detalle de la factura " + this.facturaSelected.getCodigo()
                    + " emitida el " + dateFormat.format(this.facturaSelected.getFechaEmision()),
                    urlDestinoReporte, nombreReporte);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha enviado factura", null));

        } catch (JRException | FileNotFoundException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, no se pudo enviar factura", e.getMessage()));
        }
        this.cancelar();

    }

    public void facturaPDF2() throws JRException, IOException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String nombreReporte = cliente.getCodigo().toString() + this.facturaSelected.getCodigo().toString() + ".pdf";
        String urlDestinoReporte = "D:\\reporte" + nombreReporte;

        List<ModeloFactura> facturaPDF = new ArrayList<ModeloFactura>();
        List<ModeloDetalleFactura> detalleFacturaPDF = new ArrayList<ModeloDetalleFactura>();

        //Cargar los datos de detalle factura
        //detallesFactura=detalleFacturaServicio.obtenerTodasPorIDFactura(1); esto ya debe estar lleno con facturaSelected
        for (DetalleFactura detallesFactura1 : detallesFactura) {
            detalleFacturaPDF.add(new ModeloDetalleFactura(detallesFactura1.getDescripcionServicio(),
                    detallesFactura1.getPrecioUnitario(),
                    detallesFactura1.getCantidad(),
                    detallesFactura1.getPrecioTotal()));
        }

        //Cargar los datos de clientes, factura y detalles de factura en el 
        facturaPDF.add(new ModeloFactura(this.cliente.getNombre(),
                this.cliente.getIdentificacion(),
                this.cliente.getDireccion(), this.cliente.getTelefono(),
                this.facturaSelected.getCodigo(),
                this.facturaSelected.getFechaEmision(),
                this.facturaSelected.getCostoTotal(),
                detalleFacturaPDF));

        //detallesFactura=detalleFacturaServicio.obtenerTodasPorIDFactura(1); esto ya debe estar lleno con facturaSelected
        JasperPrint jasperPrint;

        try {
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(facturaPDF);
            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteFactura.jasper");
            jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);

            JasperExportManager.exportReportToPdfFile(jasperPrint, urlDestinoReporte);
            
            Correo correo = new Correo();

            correo.EnviarCorreoConArchivoAdjunto(cliente.getCorreoElectronico(),
                    "Factura SAIV", "Detalle de la factura " + this.facturaSelected.getCodigo()
                    + " emitida el " + dateFormat.format(this.facturaSelected.getFechaEmision()),
                    urlDestinoReporte, nombreReporte);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha enviado factura", null));

        } catch (JRException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, no se exportar factura", e.getMessage()));
        }
        this.cancelar();

    }

}
