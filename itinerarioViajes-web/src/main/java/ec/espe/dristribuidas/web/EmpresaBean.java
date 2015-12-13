/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;


import ec.espe.dristribuidas.modelo.Empresa;
import ec.espe.dristribuidas.servicios.EmpresaServicio;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped; 
/**
 *
 * @author RAUL
 */
@ViewScoped
@ManagedBean
public class EmpresaBean {
    @EJB
    private EmpresaServicio empresaservicio;
    private List<Empresa> empresa;

    public List<Empresa> getEmpresa() {
        return empresa;
    }

    public void setEmpresa(List<Empresa> empresa) {
        this.empresa = empresa;
    }
    @PostConstruct
   public void inicializar(){
    empresa=empresaservicio.obtenerTodas();
   }

}
