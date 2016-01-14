/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.reportes.ModeloDetalleFactura;
import ec.espe.dristribuidas.reportes.ModeloFactura;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.util.IOUtils;
import static org.eclipse.jdt.internal.compiler.parser.Parser.name;

/**
 *
 * @author ale
 */
@ViewScoped
@ManagedBean
public class PruebaBean implements Serializable {

    List<ModeloFactura> facturaPDF;
    List<ModeloDetalleFactura> dfacturaPDF;

    public void cargar() {
        facturaPDF = new ArrayList<ModeloFactura>();
        dfacturaPDF = new ArrayList<ModeloDetalleFactura>();
        dfacturaPDF.add(new ModeloDetalleFactura("boleto", new BigDecimal(1.5), 2, new BigDecimal(3)));
        facturaPDF.add(new ModeloFactura("Ale", "0503333", "Lata", "09999", 1, new Date(), new BigDecimal(3), dfacturaPDF));
    }

    public void crearReporteBoletos() {

        cargar();

        String nombreReporte2 = "reporte2.pdf";
        //String urlDestinoReporte2 = "C:\\Users\\ale\\Documents\\NetBeansProjects\\Distri_Itinerario_Viaje\\itinerarioViajes-web\\src\\main\\webapp\\reportesPDF" + nombreReporte2;
       // String aux = FacesContext.getCurrentInstance().getExternalContext().getRealPath("a.pdf");
        //aux = aux.substring(0, aux.length() - 5) + "\\reportesPDF\\";
        //String urlDestinoReporte2 = aux + nombreReporte2;
        String urlDestinoReporte2 ="C:\\Users\\ale\\Documents\\NetBeansProjects\\"+
                "Distri_Itinerario_Viaje\\itinerarioViajes-web\\src\\main\\webapp\\reportesPDF\\"+
                nombreReporte2;
        JasperPrint jasperPrint2;

        try {
            JRBeanCollectionDataSource beanCollectionDataSource2 = new JRBeanCollectionDataSource(facturaPDF);
            String reportPath2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteFactura.jasper");
            jasperPrint2 = JasperFillManager.fillReport(reportPath2, new HashMap(), beanCollectionDataSource2);

            JasperExportManager.exportReportToPdfFile(jasperPrint2, urlDestinoReporte2);
            System.out.println("Path: " + urlDestinoReporte2);
            //FacesContext context = FacesContext.getCurrentInstance();context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Path de jasper",aux ));

        } catch (JRException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error, no se ha podido enviar itinerario", e.getMessage()));
        }

    }

    public void PDF() throws IOException, JRException {

        cargar();
        JasperPrint jasperPrint2;

        JRBeanCollectionDataSource beanCollectionDataSource2 = new JRBeanCollectionDataSource(facturaPDF);
        String reportPath2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteFactura.jasper");
        jasperPrint2 = JasperFillManager.fillReport(reportPath2, new HashMap(), beanCollectionDataSource2);

        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=reporte1.pdf");

        FacesContext.getCurrentInstance().responseComplete();

        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint2, servletOutputStream);
        System.out.println("All done the report is done");
        servletOutputStream.flush();
        servletOutputStream.close();
        FacesContext.getCurrentInstance().responseComplete();

    }

}
