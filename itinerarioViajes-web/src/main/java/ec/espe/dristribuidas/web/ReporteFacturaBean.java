/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Cliente;
import ec.espe.dristribuidas.modelo.DetalleFactura;
import ec.espe.dristribuidas.modelo.Factura;
import ec.espe.dristribuidas.modelo.Lugar;
import ec.espe.dristribuidas.servicios.DetalleFacturaServicio;
import ec.espe.dristribuidas.servicios.FacturaServicio;
import ec.espe.dristribuidas.servicios.LugarServicio;
import ec.espe.dristribuidas.utils.Correo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
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
public class ReporteFacturaBean extends BaseBean implements Serializable {

    @EJB
    private FacturaServicio facturaServicio;
    private List<Factura> facturas; //Todas las facturas de la table Facturas
    private List<Factura> facturasCliente; //Todas las facturas asociadas a un cliente
    private Factura factura;
    private Factura facturaSelected;

    @EJB
    private DetalleFacturaServicio detalleFacturaServicio;
    private List<DetalleFactura> detallesFactura; //Todos los detalles de una factura

    @EJB
    private LugarServicio lugarServicio;
    private List<Lugar> lugares; //Todos los detalles de una factura

    ValidacionesInputBean validacion = new ValidacionesInputBean();

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean datosLogin;

    JasperPrint jasperPrint;
    JasperPrint jasperPrint2;
    
    

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

        facturasCliente = facturaServicio.obtenerTodasPorIDCliente(this.cliente.getCodigo());
    }

    public void cargar() throws JRException, FileNotFoundException {

        List<Cliente> clientes;

        clientes = new ArrayList<Cliente>();

        clientes.add(cliente);
        
        detallesFactura=detalleFacturaServicio.obtenerTodasPorIDFactura(1);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detallesFactura);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("ReportePrueba2_subreport1.jasper");
        jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);

        JRBeanCollectionDataSource beanCollectionDataSource2 = new JRBeanCollectionDataSource(clientes);
        String reportPath2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("ReportePrueba2_subreporte2.jasper");
        jasperPrint2 = JasperFillManager.fillReport(reportPath2, new HashMap(), beanCollectionDataSource2);

        List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
        String urlDestinoReporte = "D:\\reporte" + cliente.getIdentificacion() + ".pdf";

        jprintlist.add(jasperPrint);
        jprintlist.add(jasperPrint2);

        
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);

        OutputStream output = new FileOutputStream(new File(urlDestinoReporte));

        exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);
        exporter.exportReport();
        
        
        /*
         JasperReport jasperMasterReport = JasperCompileManager.compileReport("");
         JasperReport jasperSubReport = JasperCompileManager.compileReport("");

         Map<String, Object> parameters = new HashMap<String, Object>();

         parameters.put("subreportParameter", jasperSubReport);      
         */
        

//jasperPrint= JasperFillManager.fillReport(
        //        "C:\\Users\\ale\\Documents\\NetBeansProjects\\Distri_Itinerario_Viaje\\itinerarioViajes-web\\src\\main\\webapp\\ReportePrueba2.jasper", 
        //        new HashMap(), beanCollectionDataSource);

        /*
        
         JRBeanCollectionDataSource beanCollectionDataSource= new JRBeanCollectionDataSource(lugares);
         String reportPath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("ReportePrueba2.jasper");
         jasperPrint= JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
         */
    }

    public void PDF() throws JRException, IOException {
        cargar();
        /*
         String urlDestinoReporte;
         String nombreReporte;

         urlDestinoReporte = "D:\\reporte" + cliente.getIdentificacion() + ".pdf";
         nombreReporte = cliente.getIdentificacion() + ".pdf";
         JasperExportManager.exportReportToPdfFile(jasperPrint, urlDestinoReporte);

         /*
         Correo correo = new Correo();
        
         correo.EnviarCorreoConArchivoAdjunto(cliente.getCorreoElectronico(), 
         "Factura itinerario", "Detalle de la factura", urlDestinoReporte, nombreReporte);
         */
    }

}
