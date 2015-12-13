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
import javax.faces.bean.ViewScoped; //siempre usar esta libreria



/**
 *
 * @author Luig Rocha
 */
@ViewScoped
@ManagedBean
public class ejemplobean {

    /**
     * Creates a new instance of ejemplobean
     */
    @EJB
    private EmpresaServicio sedeservicio;
    private List<Empresa> sede;

    public List<Empresa> getSede() {
        return sede;
    }

    public void setSede(List<Empresa> sede) {
        this.sede = sede;
    }
    @PostConstruct
   public void inicializar(){
    sede=sedeservicio.obtenerTodas();
   }
    
}
