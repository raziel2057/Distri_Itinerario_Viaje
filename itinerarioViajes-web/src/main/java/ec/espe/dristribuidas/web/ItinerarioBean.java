/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import ec.espe.dristribuidas.modelo.Boleto;
import ec.espe.dristribuidas.modelo.Cliente;
import ec.espe.dristribuidas.modelo.DetalleFactura;
import ec.espe.dristribuidas.modelo.DetalleItinerario;
import ec.espe.dristribuidas.modelo.Factura;
import ec.espe.dristribuidas.modelo.Frecuencia;
import ec.espe.dristribuidas.modelo.Itinerario;
import ec.espe.dristribuidas.modelo.Lugar;
import ec.espe.dristribuidas.reportes.ModeloDetalleFactura;
import ec.espe.dristribuidas.reportes.ModeloDetalleItinerario;
import ec.espe.dristribuidas.reportes.ModeloFactura;
import ec.espe.dristribuidas.reportes.ModeloItinerario;
import ec.espe.dristribuidas.servicios.BoletoServicio;
import ec.espe.dristribuidas.servicios.BusServicio;
import ec.espe.dristribuidas.servicios.DetalleFacturaServicio;
import ec.espe.dristribuidas.servicios.DetalleItinerarioServicio;
import ec.espe.dristribuidas.servicios.FacturaServicio;
import ec.espe.dristribuidas.servicios.FrecuenciaServicio;
import ec.espe.dristribuidas.servicios.ItinerarioServicio;
import ec.espe.dristribuidas.servicios.LugarServicio;
import ec.espe.dristribuidas.servicios.RutaServicio;
import ec.espe.dristribuidas.utils.Correo;
import ec.espe.dristribuidas.utils.ItineararioString;
import ec.espe.dristribuidas.utils.ItinerarioUtil;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class ItinerarioBean implements Serializable {

    @EJB
    private ItinerarioServicio itinerarioServicio;
    private List<Itinerario> itinearios;
    private List<Itinerario> itineariosPorCliente;
    private Itinerario itinerario;
    private Date fechaSalida;
    private Date fechaActual;
    private Date fechaMaxima;
    private List<ItinerarioUtil> posiblesItinerarios;
    private List<ItineararioString> posiblesItinerariosString;
    private ItineararioString posibleItinerarioString;

    @EJB
    private DetalleItinerarioServicio detalleItinerarioServicio;

    @EJB
    private LugarServicio lugarServicio;
    private List<Lugar> lugaresSalida;
    private List<Lugar> lugaresLlegada;
    private Integer codigoLugarSalida;
    private Integer codigoLugarLlegada;

    @EJB
    private FrecuenciaServicio frecuenciaServicio;
    private List<Frecuencia> posibleItinerario;
    private List<Frecuencia> frecuencias;
    private List<Frecuencia> rutaFrecuancias;
    private Frecuencia rutaFrecuenciaSelected;

    @EJB
    private BusServicio busServicio;

    @EJB
    private RutaServicio rutaServicio;

    @EJB
    private BoletoServicio boletoServicio;
    private List<Boleto> boletos;
    private boolean comprandoBoleto;
    private List<Boleto> boletosPorBusFrec;
    private Boleto boletoSelected;
    private List<Boleto> boletosComprados;
    private int indexBoletoFrec;

    @EJB
    private FacturaServicio facturaServicio;
    private List<Factura> facturas;
    private Factura factura;

    @EJB
    private DetalleFacturaServicio detalleFacturaServicio;
    private List<DetalleFactura> detalleFacturas;
    private DetalleFactura detalleFactura;

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean datosLogin;

    private Cliente cliente;

    private boolean enMostrarFactura;
    private ModeloFactura modeloFactura;

    List<ModeloItinerario> itinerarioPDF;
    List<ModeloDetalleItinerario> detalleItinerarioPDF;
    List<ModeloFactura> facturaPDF;
    List<ModeloDetalleFactura> detalleFacturaPDF;

    private String nombreReporte;
    private String urlDestinoReporte;

    private JasperPrint jasperPrint;
    
    public List<Itinerario> getItinearios() {
        return itinearios;
    }

    public void setItinearios(List<Itinerario> itinearios) {
        this.itinearios = itinearios;
    }

    public List<Itinerario> getItineariosPorCliente() {
        return itineariosPorCliente;
    }

    public void setItineariosPorCliente(List<Itinerario> itineariosPorCliente) {
        this.itineariosPorCliente = itineariosPorCliente;
    }

    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Date getFechaMaxima() {
        return fechaMaxima;
    }

    public List<ItinerarioUtil> getPosiblesItinerarios() {
        return posiblesItinerarios;
    }

    public void setPosiblesItinerarios(List<ItinerarioUtil> posiblesItinerarios) {
        this.posiblesItinerarios = posiblesItinerarios;
    }

    public List<ItineararioString> getPosiblesItinerariosString() {
        return posiblesItinerariosString;
    }

    public void setPosiblesItinerariosString(List<ItineararioString> posiblesItinerariosString) {
        this.posiblesItinerariosString = posiblesItinerariosString;
    }

    public ItineararioString getPosibleItinerarioString() {
        return posibleItinerarioString;
    }

    public void setPosibleItinerarioString(ItineararioString posibleItinerarioString) {
        this.posibleItinerarioString = posibleItinerarioString;
    }

    public List<Frecuencia> getRutaFrecuancias() {
        return rutaFrecuancias;
    }

    public void setRutaFrecuancias(List<Frecuencia> rutaFrecuancias) {
        this.rutaFrecuancias = rutaFrecuancias;
    }

    public Frecuencia getRutaFrecuenciaSelected() {
        return rutaFrecuenciaSelected;
    }

    public void setRutaFrecuenciaSelected(Frecuencia rutaFrecuenciaSelected) {
        this.rutaFrecuenciaSelected = rutaFrecuenciaSelected;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }

    public List<Lugar> getLugaresSalida() {
        return lugaresSalida;
    }

    public void setLugaresSalida(List<Lugar> lugaresSalida) {
        this.lugaresSalida = lugaresSalida;
    }

    public List<Lugar> getLugaresLlegada() {
        return lugaresLlegada;
    }

    public void setLugaresLlegada(List<Lugar> lugaresLlegada) {
        this.lugaresLlegada = lugaresLlegada;
    }

    public Integer getCodigoLugarSalida() {
        return codigoLugarSalida;
    }

    public void setCodigoLugarSalida(Integer codigoLugarSalida) {
        this.codigoLugarSalida = codigoLugarSalida;
    }

    public Integer getCodigoLugarLlegada() {
        return codigoLugarLlegada;
    }

    public void setCodigoLugarLlegada(Integer codigoLugarLlegada) {
        this.codigoLugarLlegada = codigoLugarLlegada;
    }

    public List<Frecuencia> getPosibleItinerario() {
        return posibleItinerario;
    }

    public void setPosibleItinerario(List<Frecuencia> posibleItinerario) {
        this.posibleItinerario = posibleItinerario;
    }

    public List<Frecuencia> getFrecuencias() {
        return frecuencias;
    }

    public void setFrecuencias(List<Frecuencia> frecuencias) {
        this.frecuencias = frecuencias;
    }

    public LoginBean getDatosLogin() {
        return datosLogin;
    }

    public void setDatosLogin(LoginBean datosLogin) {
        this.datosLogin = datosLogin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public void setBoletos(List<Boleto> boletos) {
        this.boletos = boletos;
    }

    public boolean isComprandoBoleto() {
        return comprandoBoleto;
    }

    public void setComprandoBoleto(boolean comprandoBoleto) {
        this.comprandoBoleto = comprandoBoleto;
    }

    public List<Boleto> getBoletosPorBusFrec() {
        return boletosPorBusFrec;
    }

    public void setBoletosPorBusFrec(List<Boleto> boletosPorBusFrec) {
        this.boletosPorBusFrec = boletosPorBusFrec;
    }

    public Boleto getBoletoSelected() {
        return boletoSelected;
    }

    public void setBoletoSelected(Boleto boletoSelected) {
        this.boletoSelected = boletoSelected;
    }

    public List<Boleto> getBoletosComprados() {
        return boletosComprados;
    }

    public void setBoletosComprados(List<Boleto> boletosComprados) {
        this.boletosComprados = boletosComprados;
    }

    public int getIndexBoletoFrec() {
        return indexBoletoFrec;
    }

    public void setIndexBoletoFrec(int indexBoletoFrec) {
        this.indexBoletoFrec = indexBoletoFrec;
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

    public List<DetalleFactura> getDetalleFacturas() {
        return detalleFacturas;
    }

    public void setDetalleFacturas(List<DetalleFactura> detalleFacturas) {
        this.detalleFacturas = detalleFacturas;
    }

    public DetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public boolean isEnMostrarFactura() {
        return enMostrarFactura;
    }

    public void setEnMostrarFactura(boolean enMostrarFactura) {
        this.enMostrarFactura = enMostrarFactura;
    }

    public List<ModeloDetalleFactura> getDetalleFacturaPDF() {
        return detalleFacturaPDF;
    }

    public ModeloFactura getModeloFactura() {
        return modeloFactura;
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

        this.definirFechas();
        this.cargarLugaresSalida();
        this.cargarLugaresLlegada();
        this.posiblesItinerarios = new ArrayList<>();
        this.posiblesItinerariosString = new ArrayList<>();
        this.comprandoBoleto = false;
        this.indexBoletoFrec = 0;
    }

    public void definirFechas() {
        this.fechaActual = new Date();
        this.fechaActual.setHours(this.fechaActual.getHours() + 2);
        this.fechaMaxima = new Date();
        this.fechaMaxima.setHours(this.fechaMaxima.getHours() + 2);
        this.fechaMaxima.setMonth(this.fechaMaxima.getMonth() + 3);
        this.fechaSalida = new Date();
        this.fechaSalida.setHours(this.fechaSalida.getHours() + 2);
    }

    public void cargarLugaresSalida() {
        this.lugaresSalida = this.lugarServicio.obtenerTodas();
        if (this.lugaresSalida.size() > 0) {
            this.codigoLugarSalida = this.lugaresSalida.get(0).getCodigo();
        }
    }

    public void cargarLugaresLlegada() {
        this.lugaresLlegada = this.lugarServicio.obtenerTodas();
        for (int i = 0; i < this.lugaresLlegada.size(); i++) {
            if (this.lugaresLlegada.get(i).getCodigo().equals(this.codigoLugarSalida)) {
                this.lugaresLlegada.remove(i);
            }
        }
        if (this.lugaresLlegada.size() > 0) {
            this.codigoLugarLlegada = this.lugaresLlegada.get(0).getCodigo();
        }
    }

    public void cargarFrecuencias() {
        this.frecuencias = this.frecuenciaServicio.obtenerTodas();
        for (int i = 0; i < this.frecuencias.size(); i++) {
            this.frecuencias.get(i).setBus(this.busServicio.obtenerPorID(this.frecuencias.get(i).getCodigoBus()));
            this.frecuencias.get(i).setRuta(this.rutaServicio.obtenerPorID(this.frecuencias.get(i).getCodigoRuta()));
        }
    }

    public void cargarFrecuenciasSeleccionadas() {
        this.rutaFrecuancias = new ArrayList<>();
        for (int i = 0; i < this.posiblesItinerarios.get(this.posibleItinerarioString.getCodigo()).getFrecuencias().size(); i++) {
            Frecuencia frecuenciaTmp = new Frecuencia();
            Frecuencia frecuenciAux = this.posiblesItinerarios.get(this.posibleItinerarioString.getCodigo()).getFrecuencias().get(i);
            try {
                BeanUtils.copyProperties(frecuenciaTmp, frecuenciAux);
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.rutaFrecuancias.add(frecuenciaTmp);
        }

        this.boletosComprados = new ArrayList<>();
    }

    public void cargarBoletos() {

        this.boletos = this.boletoServicio.obtenerTodas();
        this.boletosPorBusFrec = new ArrayList<>();
        for (int i = 0; i < this.boletos.size(); i++) {
            if (this.boletos.get(i).getCodigoFrecuencia().equals(this.rutaFrecuenciaSelected.getCodigo())
                    && this.boletos.get(i).getAsiento().getCodigoBus().equals((this.rutaFrecuenciaSelected.getCodigoBus()))) {
                this.boletosPorBusFrec.add(this.boletos.get(i));
            }
        }

    }

    public void comprar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            double costoTotal = 0;
            for (Boleto b : this.boletosComprados) {

                b.setEstado("O");
                this.boletoServicio.actualiarBoleto(b);
                costoTotal += b.getCosto().doubleValue();

            }

            Itinerario itinerarioTmp = new Itinerario();
            itinerarioTmp.setCodigoCliente(this.cliente.getCodigo());
            itinerarioTmp.setCostoTotal(BigDecimal.valueOf(costoTotal));
            itinerarioTmp.setFechaContratacion(new Date());
            itinerarioTmp.setCantidad(1);

            Factura facturaTmp = new Factura();
            facturaTmp.setCodigoCliente(this.cliente.getCodigo());
            facturaTmp.setFechaEmision(new Date());
            facturaTmp.setCostoTotal(BigDecimal.valueOf(costoTotal));

            this.itinerarioServicio.crearItinerario(itinerarioTmp);
            this.facturaServicio.crearFactura(facturaTmp);
            this.itinearios = itinerarioServicio.obtenerTodas();
            this.itinerario = this.itinearios.get(this.itinearios.size() - 1);
            this.facturas = this.facturaServicio.obtenerTodas();
            this.factura = this.facturas.get(this.facturas.size() - 1);
            int sec = 0;

            detalleFacturaPDF = new ArrayList<ModeloDetalleFactura>();

            for (Boleto b : this.boletosComprados) {
                sec++;
                DetalleItinerario detalleItinerarioTmp = new DetalleItinerario();
                detalleItinerarioTmp.setCodigoBoleto(b.getCodigo());
                detalleItinerarioTmp.setCodigoItinerario(this.itinerario.getCodigo());
                detalleItinerarioTmp.setTiempoEstadiaHoras(0);
                detalleItinerarioTmp.setSecuencial(sec);

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                String desc = "Boleto No: " + b.getCodigo()
                        + " Bus: " + b.getFrecuencia().getBus().getEmpresa().getNombre()
                        + "-" + b.getFrecuencia().getCodigoBus()
                        + " Fecha S: " + dt.format(b.getFrecuencia().getFechaSalida());

                DetalleFactura detalleFacturaTmp = new DetalleFactura();
                detalleFacturaTmp.setCodigoFactura(this.factura.getCodigo());
                detalleFacturaTmp.setDescripcionServicio(desc);
                detalleFacturaTmp.setPrecioUnitario(b.getCosto());
                detalleFacturaTmp.setCantidad(1);
                detalleFacturaTmp.setPrecioTotal(b.getCosto());

                this.detalleItinerarioServicio.crearDetalleItinerario(detalleItinerarioTmp);
                this.detalleFacturaServicio.crearDetalleFactura(detalleFacturaTmp);
                detalleFacturaPDF.add(new ModeloDetalleFactura(detalleFacturaTmp.getDescripcionServicio(),
                        detalleFacturaTmp.getPrecioUnitario(),
                        detalleFacturaTmp.getCantidad(),
                        detalleFacturaTmp.getPrecioTotal()));

            }

            //Crear el reporte con los boletos, cargar datos en una lista
            itinerarioPDF = new ArrayList<ModeloItinerario>();
            detalleItinerarioPDF = new ArrayList<ModeloDetalleItinerario>();
            facturaPDF = new ArrayList<ModeloFactura>();

            //Cargar los datos de los boletos
            for (Boleto b : this.boletosComprados) {
                detalleItinerarioPDF.add(new ModeloDetalleItinerario(b.getFrecuencia().getRuta().getNombre(),
                        b.getFrecuencia().getFechaSalida(),
                        b.getFrecuencia().getFechaLlegada(),
                        b.getCodigo(),
                        b.getFrecuencia().getCodigoBus(),
                        b.getFrecuencia().getBus().getEmpresa().getNombre(),
                        b.getAsiento().getNombre(),
                        b.getCosto()));
            }

            //----------------------Crear datos boletos y factura
            //Cargar los datos de clientes, y boletos 
            itinerarioPDF.add(new ModeloItinerario(this.cliente.getNombre(),
                    this.cliente.getIdentificacion(),
                    this.cliente.getDireccion(),
                    this.cliente.getTelefono(),
                    this.itinerario.getCodigo(),
                    detalleItinerarioPDF));

            //crear datos cliente y factura
            modeloFactura = new ModeloFactura(this.cliente.getNombre(),
                    this.cliente.getIdentificacion(),
                    this.cliente.getDireccion(), this.cliente.getTelefono(),
                    this.factura.getCodigo(),
                    this.factura.getFechaEmision(),
                    this.factura.getCostoTotal(),
                    detalleFacturaPDF);
            facturaPDF.add(modeloFactura);
            crearReporteBoletos();
            enMostrarFactura = true;

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Se copraron " + this.boletosComprados.size() + " boletos. ", "Los tickets de los boletos comprados fueron enviados a su correo."));

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));

        } finally {
            this.reset();
        }
    }

    public void crearReporteBoletos() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = new Date();

        String nombreReporte2 = "reporte" + cliente.getCodigo().toString() + "itinerario" + this.itinerario.getCodigo() + ".pdf";
        String urlDestinoReporte2 = "D:\\" + nombreReporte2;
        JasperPrint jasperPrint2;

        try {
            JRBeanCollectionDataSource beanCollectionDataSource2 = new JRBeanCollectionDataSource(itinerarioPDF);
            String reportPath2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteItinerario.jasper");
            jasperPrint2 = JasperFillManager.fillReport(reportPath2, new HashMap(), beanCollectionDataSource2);

            JasperExportManager.exportReportToPdfFile(jasperPrint2, urlDestinoReporte2);

            Correo correo = new Correo();
            correo.EnviarCorreoConArchivoAdjunto(cliente.getCorreoElectronico(),
                    "Itinerario SAIV", "Detalle del itinerario " + this.itinerario.getCodigo()
                    + " emitido el " + dateFormat.format(fechaActual),
                    urlDestinoReporte2, nombreReporte2);

        } catch (JRException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, no se ha podido enviar itinerario", e.getMessage()));
        }

        nombreReporte = "reporte" + cliente.getCodigo().toString() + "factura" + this.factura.getCodigo().toString() + ".pdf";
        urlDestinoReporte = "D:\\" + nombreReporte;

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(facturaPDF);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteFactura.jasper");

        try {
            jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);

            JasperExportManager.exportReportToPdfFile(jasperPrint, urlDestinoReporte);
        } catch (Exception e) {

        }

    }

    public void enviarFacturaACorreo() {

        Correo correo = new Correo();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        correo.EnviarCorreoConArchivoAdjunto(cliente.getCorreoElectronico(),
                "Factura SAIV", "Factura por compra de boletos de Itinerario. "
                + " Fecha de compra: " + dateFormat.format(new Date()),
                urlDestinoReporte, nombreReporte);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha enviado la factura", null));
        
        this.resetUltimoAceptar();

    }
    
    public void descargarFactura() throws IOException, JRException{
        
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=reporte1.pdf");

        FacesContext.getCurrentInstance().responseComplete();

        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        servletOutputStream.flush();
        servletOutputStream.close();
        FacesContext.getCurrentInstance().responseComplete();
        this.resetUltimoAceptar();

    }
        

    public void buscarItinerarios() {
        this.posiblesItinerarios = new ArrayList<>();
        this.cargarFrecuencias();
        this.bi(this.codigoLugarSalida, this.posibleItinerario);
        this.posiblesItinerariosString = new ArrayList<>();
        for (int i = 0; i < this.posiblesItinerarios.size(); i++) {
            ItineararioString itiStringTmp = new ItineararioString();
            itiStringTmp.setCodigo(i);
            itiStringTmp.setDescripcion(this.posiblesItinerarios.get(i).toString());
            itiStringTmp.setCosto(this.posiblesItinerarios.get(i).costoTotal());
            itiStringTmp.setDistancia(this.posiblesItinerarios.get(i).distanciaTotal());
            itiStringTmp.setTiempo(this.posiblesItinerarios.get(i).tiempoTotal());
            itiStringTmp.setNumeroConexiones(this.posiblesItinerarios.get(i).getFrecuencias().size());

            this.posiblesItinerariosString.add(itiStringTmp);

            System.out.println(this.posiblesItinerarios.get(i).toString() + "Horas" + this.posiblesItinerarios.get(i).tiempoTotal());
        }
    }

    public void bi(Integer codigoLugarSalida, List<Frecuencia> posibleItinerario) {
        for (int i = 0; i < this.frecuencias.size(); i++) {

            if (this.frecuencias.get(i).getRuta().getCodigoLugarSalida().equals(codigoLugarSalida)) {

                if (codigoLugarSalida.equals(this.codigoLugarSalida)) {
                    posibleItinerario = new ArrayList<>();
                }

                if (posibleItinerario.size() > 0) {
                    int flag = 0;
                    for (int j = 0; j < posibleItinerario.size(); j++) {
                        if (flag == 1) {
                            posibleItinerario.remove(j);
                            j--;
                        }
                        if (posibleItinerario.get(j).getRuta().getCodigoLugarDestino().equals(codigoLugarSalida)) {
                            flag = 1;
                        }
                    }
                }

                boolean flagBP = true;

                if (posibleItinerario.size() > 0) {
                    for (int j = 0; j < posibleItinerario.size(); j++) {

                        if ((posibleItinerario.get(j).getRuta().getCodigoLugarSalida().equals(this.frecuencias.get(i).getRuta().getCodigoLugarDestino())) || (posibleItinerario.get(j).getRuta().getCodigoLugarDestino().equals(this.frecuencias.get(i).getRuta().getCodigoLugarDestino()))) {
                            flagBP = false;
                        }

                    }
                }

                boolean flagFecha = true;

                if (posibleItinerario.size() > 0) {
                    if (this.frecuencias.get(i).getFechaSalida().before(posibleItinerario.get(posibleItinerario.size() - 1).getFechaLlegada())) {
                        flagFecha = false;
                    }
                } else {
                    if (this.frecuencias.get(i).getFechaSalida().before(this.fechaSalida)) {
                        flagFecha = false;
                    }
                }

                if (flagBP && flagFecha) {
                    posibleItinerario.add(this.frecuencias.get(i));
                    if (this.frecuencias.get(i).getRuta().getCodigoLugarDestino().equals(this.codigoLugarLlegada)) {
                        List<Frecuencia> lTmp = new ArrayList<>();
                        for (int l = 0; l < posibleItinerario.size(); l++) {
                            Frecuencia fd = new Frecuencia();
                            Frecuencia fo = posibleItinerario.get(l);
                            try {
                                BeanUtils.copyProperties(fd, fo);
                                lTmp.add(fd);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        ItinerarioUtil tmp = new ItinerarioUtil();

                        tmp.setFrecuencias(lTmp);
                        this.posiblesItinerarios.add(tmp);
                    } else {
                        this.bi(this.frecuencias.get(i).getRuta().getCodigoLugarDestino(), posibleItinerario);
                    }
                }
            }
        }
    }

    public void comprando() {
        this.comprandoBoleto = true;
        for (int i = 0; i < this.boletosComprados.size(); i++) {
            if (this.boletosComprados.get(i).getCodigoFrecuencia().equals(this.boletoSelected.getCodigoFrecuencia())) {
                this.boletosComprados.remove(i);
                i--;
            }
        }
        Boleto boletoTmp = new Boleto();
        try {
            BeanUtils.copyProperties(boletoTmp, this.boletoSelected);
            this.boletosComprados.add(boletoTmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reset() {
        this.comprandoBoleto = false;
        this.boletos = null;
        this.indexBoletoFrec = 0;
        this.boletosComprados = null;
        this.facturaPDF = null;
        this.detalleFacturaPDF = null;
        this.enMostrarFactura = false;
    }

    public void resetUltimoAceptar() {
        this.comprandoBoleto = false;
        this.boletos = null;
        this.indexBoletoFrec = 0;
        this.boletosComprados = null;
        this.enMostrarFactura = false;
    }

    public void resetCompraBoletos() {
        this.boletosComprados.removeAll(this.boletosComprados);
    }

    public boolean estaComprado(Integer codigoBoleto) {
        boolean flag = false;
        if (this.boletosComprados != null) {
            for (Boleto b : this.boletosComprados) {
                if (b.getCodigo().equals(codigoBoleto)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
